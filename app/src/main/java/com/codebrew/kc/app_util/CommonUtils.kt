package com.codebrew.kc.app_util


import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import java.io.IOException
import java.util.*


class CommonUtils {
    companion object {




        fun isNetworkConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }



         fun getaddress(mContext: Context,latitude: Double, longitude: Double): String {

            val geocoder: Geocoder
            val addresses: List<Address>
            var address: Address
            var addressData = ""
            geocoder = Geocoder(mContext, Locale.getDefault())

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1) // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                for (i in addresses.indices) {
                    address = addresses[i]
                    if (address.getAddressLine(0) != null) {
                        addressData = address.getAddressLine(0)
                        break
                    }
                }

                return addressData
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return addressData


        }
    }
}
