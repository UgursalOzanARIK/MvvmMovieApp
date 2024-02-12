package com.ozanarik.mvvmmovieapp.utils

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.ozanarik.mvvmmovieapp.utils.Extensions.Companion.showSnackbar

class Extensions {

    companion object{

        fun Double.doubleToFloat():Float{
            return this.toFloat()
        }

        fun View.showSnackbar(msg:String,length:Int = Snackbar.LENGTH_LONG){
            Snackbar.make(this,msg,length).show()
        }
        fun Fragment.showSnackbar(msg:String,length: Int = Snackbar.LENGTH_LONG){
            view?.let { Snackbar.make(it,msg,length).show() }
        }

        fun View.setVisibility(visibility:Boolean){
            this.visibility = when(visibility){
                true->{
                    View.VISIBLE
                }
                false->{
                    View.INVISIBLE
                }
            }
        }
        fun List<Int>.formatEpisodeTime():String{
            return this.joinToString ( ", " ){ "$it min" }
        }

    }



}