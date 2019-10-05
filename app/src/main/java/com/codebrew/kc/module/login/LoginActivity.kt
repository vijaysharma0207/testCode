package com.codebrew.kc.module.login

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.ViewModelProviders
import com.codebrew.kc.BR
import com.codebrew.kc.R
import com.codebrew.kc.appData.constants.PrefenceConstants
import com.codebrew.kc.appData.preferences.PreferenceHelper
import com.codebrew.kc.app_util.extension.launchActivity
import com.codebrew.kc.app_util.extension.onSnackbar
import com.codebrew.kc.base.BaseActivity
import com.codebrew.kc.databinding.ActivityLoginBinding
import com.codebrew.kc.di.ViewModelProviderFactory
import com.codebrew.kc.module.home.MainActivity
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.*
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(), LoginNavigator , Validator.ValidationListener {

    @Inject
    lateinit var factory: ViewModelProviderFactory

    @Inject
    lateinit var mprefHelper: PreferenceHelper

    @NotEmpty(message = "Please enter UserName")
    @Order(1)
    private lateinit var userNameEditText: EditText

    @NotEmpty
    @Password(scheme = Password.Scheme.ANY)
    @Order(2)
    @Length( min = 4, message = "Input must be between 4 and 12 characters")
    private lateinit var passwordEditText: EditText

    // Validation button_login
    private val validator = Validator(this)

    private lateinit var viewModel: LoginViewModel

    private var mActivityLoginBinding: ActivityLoginBinding? = null


    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun getViewModel(): LoginViewModel {
        viewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
        return viewModel
    }

    override fun onSucess() {

        if(check_remember.isChecked)
        {
            mprefHelper.setkeyValue(PrefenceConstants.USERNAME,userNameEditText.text.toString().trim())
            mprefHelper.setkeyValue(PrefenceConstants.PASSWORD,passwordEditText.text.toString().trim())
        }

        mprefHelper.setkeyValue(PrefenceConstants.REMEMBER_INFO,check_remember.isChecked)

        launchActivity<MainActivity>()
        finishAffinity()
    }

    override fun onError(message: String) {

        mActivityLoginBinding?.root?.onSnackbar(message)
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        val error = errors?.get(0)
        val message = error?.getCollatedErrorMessage(this)
        val editText = error?.view as EditText
        editText.error = message
        editText.requestFocus()
    }

    override fun onValidationSucceeded() {
        hideKeyboard()


        val param=HashMap<String,String>()
        param["username"]=userNameEditText.text.toString()
        param["password"]=passwordEditText.text.toString()
        param["usertype"]="accountAdmin"
        param["_extend"]="user"

        if(isNetworkConnected)
        {
            viewModel.loginApi(param)
        }

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mActivityLoginBinding = viewDataBinding
        viewModel.navigator = this

        // Validator
        validator.validationMode = Validator.Mode.IMMEDIATE
        // Listeners
        validator.setValidationListener(this)

        userNameEditText = findViewById(R.id.edit_username)
        passwordEditText = findViewById(R.id.edit_pswr)

        btn_login.setOnClickListener {
            validator.validate()
        }

        if(mprefHelper.onRememberInfo())
        {
            userNameEditText.setText(mprefHelper.getKeyValue(PrefenceConstants.USERNAME,PrefenceConstants.TYPE_STRING).toString())
            passwordEditText.setText(mprefHelper.getKeyValue(PrefenceConstants.PASSWORD,PrefenceConstants.TYPE_STRING).toString())

            check_remember.isChecked=true
        }


    }
}
