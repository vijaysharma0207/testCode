package com.codebrew.kc.module.home

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codebrew.kc.BR
import com.codebrew.kc.R
import com.codebrew.kc.app_interface.OnDialogButtonClickListener
import com.codebrew.kc.app_util.DialogsUtil
import com.codebrew.kc.app_util.extension.launchActivity
import com.codebrew.kc.app_util.extension.onSnackbar
import com.codebrew.kc.base.BaseActivity
import com.codebrew.kc.databinding.ActivityMainBinding
import com.codebrew.kc.di.ViewModelProviderFactory
import com.codebrew.kc.module.home.adapter.PageAdapter
import com.codebrew.kc.module.home.adapter.PageListener
import com.codebrew.kc.module.home.adapter.UserListAdapter
import com.codebrew.kc.module.login.LoginActivity
import com.codebrew.kc.retrofit.model.Pagination
import com.codebrew.kc.retrofit.model.UserListResponse
import com.codebrew.kc.retrofit.model.custom.PageStatusModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator,
    OnDialogButtonClickListener {


    @Inject
    lateinit var factory: ViewModelProviderFactory

    @Inject
    lateinit var dialogUtil: DialogsUtil

    private var mHomeViewModel: MainViewModel? = null
    private var mActivityHomeBinding: ActivityMainBinding? = null

    private lateinit var userAdapter: UserListAdapter
    private lateinit var pageAdapter: PageAdapter

    private var mCurrentPage = 0
    private var mTotalPage = 0
    private var mPageCount = "5"

    private var userData = mutableListOf<UserListResponse>()
    private var mPageStatusList = mutableListOf<PageStatusModel>()

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModel(): MainViewModel {
        mHomeViewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
        return mHomeViewModel as MainViewModel
    }

    override fun onErrorOccur(message: String) {
        mActivityHomeBinding?.root?.onSnackbar(message)
    }

    override fun onSessionExpire() {
        openActivityOnTokenExpire()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mActivityHomeBinding = viewDataBinding
        mHomeViewModel?.navigator = this

        initialize()
    }

    private fun initialize() {

        userAdapter = UserListAdapter()
        rv_user_list.adapter = userAdapter

        pageAdapter = PageAdapter(PageListener(
            {

                mCurrentPage = it.page.toInt() - 1
                loadUserData(mCurrentPage)
            },
            {
                mPageStatusList.mapIndexed { index, pageStatusModel ->

                    if (pageStatusModel.status) {
                        mCurrentPage = pageStatusModel.page.toInt()
                    }
                }
                loadUserData(mCurrentPage)
            }
        ))


        linear_logout.setOnClickListener {
            dialogUtil.openAlertDialog(
                this, getString(R.string.logout_text), getString(R.string.ok),
                "Cancel", this
            )
        }

        rv_page_list.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        rv_page_list.adapter = pageAdapter

        userListObserver()
        getUserList("0")
    }

    private fun loadUserData(pos: Int) {

        mCurrentPage++

        if (mTotalPage > mCurrentPage) {

            //call api as per count
            if (pos == 0) {
                getUserList("0")
            } else {
                getUserList(((pos * 5) + 1).toString())
            }

            //refresh count list
            mPageStatusList.mapIndexed { _, value ->
                value.status = value.page == mCurrentPage.toString()
            }
            pageAdapter.notifyDataSetChanged()

        } else
            mActivityHomeBinding?.root?.onSnackbar("Last Page")

    }

    private fun getUserList(start: String) {
        if (isNetworkConnected) {
            viewModel.getUserList(start, mPageCount)
        }
    }


    private fun userListObserver() {
        // Create the observer which updates the UI.
        val catObserver = Observer<List<UserListResponse>> { resource ->
            // Update the UI, in this case, a TextView.

            if (resource.isNotEmpty()) {
                userData.addAll(resource)
                userAdapter.submitMessageList(resource)
            } else {
                mActivityHomeBinding?.root?.onSnackbar(getString(R.string.no_data))
            }
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mHomeViewModel?.userListLiveData?.observe(this, catObserver)

    }

    override fun getTotalCount(pagination: Pagination) {
        mTotalPage = pagination.totalPages

        if (mPageStatusList.isEmpty()) {
            mPageStatusList.addAll(populateCount(pagination.totalPages) ?: emptyList())
            pageAdapter.addFooterAndSubmitList(mPageStatusList)
        }
    }

    private fun populateCount(count: Int): MutableList<PageStatusModel>? {

        val item = mutableListOf<PageStatusModel>()

        for (i in 1 until count) {
            if (i == 1) {
                item.add(PageStatusModel(i.toString(), true))
            } else {
                item.add(PageStatusModel(i.toString(), false))
            }
        }
        return item
    }

    override fun onPositiveButtonClicked() {
        if (isNetworkConnected) {
            viewModel.logOut()
        }
    }

    override fun onNegativeButtonClicked() {

    }

    override fun onLogout(message: String) {
        mActivityHomeBinding?.root?.onSnackbar(message)
        launchActivity<LoginActivity> { }
        finishAffinity()
    }


}
