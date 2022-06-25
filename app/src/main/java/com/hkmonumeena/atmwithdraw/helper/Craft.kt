package com.hkmonumeena.atmwithdraw.helper

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.View
import android.view.animation.BounceInterpolator
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

/**
 * @sample startActivity
 * @sample putKey
 * @sample getKey
 * @sample clearAllKeys
 * @sample isOnline
 * @sample confirmationDialog
 * @sample textView
 * @sample editText
 * @sample imageView
 * @sample materialCardView
 * @sample view
 * @sample linearLayout
 * @sample constraintLayout
 * @sample rela tiveLayout
 * @sample recyclerView
 * @sample isValidate
 * @sample getValidatedFields
 * @author Monu Meena
 * @since 2021
 *
 */
object Craft {
    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private const val MY_PREF = "PrefPrayas"

/*    inline fun Context.toast(message: String, layout: View) {
        layout.textViewMessage.text = message

        val myToast = Toast(this)
        myToast.duration = Toast.LENGTH_SHORT
        //myToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        myToast.setGravity(Gravity.BOTTOM, 0, 0);
        myToast.view = layout
        myToast.show()
    }*/

    inline fun <reified T : Activity> Activity.startActivity() {
        startActivity(createIntent<T>())
    }

    inline fun <reified T : Activity> Context.createIntent() =
        Intent(this, T::class.java)

    fun Context.showToast(message: String?) {
        Toast.makeText(this, "$message", Toast.LENGTH_SHORT).show()
    }

    /**
     * this fun can be put a string value into  SharedPreferences
     * putKey(key?,value?)
     * @param key? String key
     * @param value? String value
     * @author Monu Meena
     * @since 2021
     *
     *
     */
    fun Context.putKey(Key: String?, Value: String?) {
        sharedPreferences =
            getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        editor = sharedPreferences?.edit()
        editor?.putString(Key, Value)
        editor?.apply()
    }

    /**
     * this fun returns a string value from  SharedPreferences
     * getKey(key?)
     * @param key? String key
     * @author Monu Meena
     * @since 2021
     *
     *
     */
    fun Context.getKey(Key: String?): String? {
        sharedPreferences =
            getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        return sharedPreferences?.getString(Key, "")
    }

    fun Context.putKeyString(Key: String?, Value: String?) {
        sharedPreferences = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        editor = sharedPreferences?.edit()
        editor?.putString(Key, Value)
        editor?.apply()
    }
    fun Context.putKeyBoolean(Key: String?, Value: Boolean) {
        sharedPreferences = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        editor = sharedPreferences?.edit()
        editor?.putBoolean(Key, Value)
        editor?.apply()
    }
    fun Context.putKeyInt(Key: String?, Value: Int) {
        sharedPreferences = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        editor = sharedPreferences?.edit()
        editor?.putInt(Key, Value)
        editor?.apply()
    }
    fun Context.putKeyLong(Key: String?, Value: Long) {
        sharedPreferences = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        editor = sharedPreferences?.edit()
        editor?.putLong(Key, Value)
        editor?.apply()
    }
    fun Context.putKeyFloat(Key: String?, Value: Float) {
        sharedPreferences = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        editor = sharedPreferences?.edit()
        editor?.putFloat(Key, Value)
        editor?.apply()
    }
    fun Context.getKeyString(Key: String?): String? {
        sharedPreferences =
            getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        return sharedPreferences?.getString(Key, "")
    }
    fun Context.getKeyBoolean(Key: String?): Boolean? {
        sharedPreferences =
            getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        return sharedPreferences?.getBoolean(Key,false)
    }
    fun Context.getKeyInt(Key: String?): Int? {
        sharedPreferences =
            getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        return sharedPreferences?.getInt(Key,0)
    }
    fun Context.getKeyLong(Key: String?): Long? {
        sharedPreferences =
            getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        return sharedPreferences?.getLong(Key,0)
    }
    fun Context.getKeyFloat(Key: String?): Float? {
        sharedPreferences =
            getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        return sharedPreferences?.getFloat(Key, 0.0F)
    }

    /**
     * this fun clears all values and keys from  SharedPreferences
     * @author Monu Meena
     * @since 2021
     *
     */
    fun Context.clearAllKeys() {
        sharedPreferences = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        editor = sharedPreferences?.edit()
        editor?.clear()
        editor?.commit()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun Context.isOnline(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
        return false
    }

    /**
     * this is a custom generic MaterialConfirmationDialog which returns values & its id and position in lambda expression
     *
     * @return requireActivity().confirmationDialog("title",0, arrayListOf<String>(), arrayListOf<String>()){
     *  position, value, id ->
     *      }
     * @author Monu Meena
     * @since 2021
     */
    fun Context.confirmationDialog(
        title: String,
        clickPosition: Int = 0,
        items: ArrayList<String>,
        id: ArrayList<String>,
        proceed: (position: Int, value: String, id: String) -> Unit
    ) {
        lateinit var itemaNamesArray: Array<String>
        lateinit var itemaIdArray: Array<String>

        if (items.size != 0) {
            itemaNamesArray = items.toTypedArray()
            itemaIdArray = id.toTypedArray()
            MaterialAlertDialogBuilder(this)
                .setTitle(title)
                .setNegativeButton("Cancel") { dialog, which ->
                    // Respond to neutral button press
                }
                .setPositiveButton("Okay") { dialog, which ->
                    // Respond to positive button press
                    if (itemaNamesArray.isNotEmpty()) {
                        val position =
                            (dialog as androidx.appcompat.app.AlertDialog).listView.checkedItemPosition
                        proceed.invoke(position, itemaNamesArray[position], itemaIdArray[position])
                    }


                }
                // Single-choice items (initialized with checked item)
                .setSingleChoiceItems(itemaNamesArray, clickPosition) { dialog, which ->
                    // Respond to item chosen
                }
                .show()
        } else {
            Toast.makeText(this, "No items found", Toast.LENGTH_SHORT).show()
        }


    }

/*
     fun loadingPopup(action: Boolean,dialog: Dialog) {
        dialog.setContentView(R.layout.loading_popup)
        var imgLoading: ImageView? = null
        var btn_cancel: CardView? = null
        var rlbg: RelativeLayout? = null
        imgLoading = dialog.findViewById(R.id.imgLoading)
        //   val draw = BitmapDrawable(resources, blurBitmap)
        imgLoading.load("https") {
            crossfade(true)
            placeholder(R.drawable.loadin_spinner)
            error(R.drawable.loadin_spinner)
        }
        dialog.setCancelable(false)

        val window = dialog.window
        dialog.window?.setBackgroundDrawableResource(R.color.black_overlay)
        //  window?.setBackgroundDrawable(draw)
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        window?.setGravity(Gravity.CENTER_VERTICAL)
        if (action){
            dialog.show()
        }else {
            dialog.dismiss()
        }


        */
/*  AppUtils.screenShot(this@HomeActivity) { bitmap ->
              val blurBitmap: Bitmap? = BlurView(application).blurBackground(bitmap, 15)
              Log.e("HomeActivity", "takeExit: "+blurBitmap);
              Log.e("HomeActivity", "takeExit: "+bitmap);
          }*//*

    }
*/

    /**
     * this method returns TextView id using  findViewById(id)
     */
    fun View.textView(id: Int): TextView {
        return findViewById(id)

    }

    /**
     * this method returns EditText id using  findViewById(id)
     */
    fun View.editText(id: Int): EditText {
        return findViewById(id)

    }

    /**
     * this method returns ImageView id using  findViewById(id)
     */
    fun View.imageView(id: Int): ImageView {
        return findViewById(id)

    }

    /**
     * this method returns MaterialCardView id using  findViewById(id)
     */
    fun View.materialCardView(id: Int): MaterialCardView {
        return findViewById(id)

    }

    /**
     * this method returns View id using  findViewById(id)
     */
    fun View.view(id: Int): View {
        return findViewById(id)

    }

    /**
     * this method returns MaterialButton id using  findViewById(id)
     */
    fun View.materialButton(id: Int): MaterialButton {
        return findViewById(id)

    }

    /**
     * this method returns LinearLayout id using  findViewById(id)
     */
    fun View.linearLayout(id: Int): LinearLayout {
        return findViewById(id)

    }

    /**
     * this method returns ConstraintLayout id using  findViewById(id)
     */
    fun View.constraintLayout(id: Int): ConstraintLayout {
        return findViewById(id)

    }

    /**
     * this method returns RelativeLayout id using  findViewById(id)
     */
    fun View.relativeLayout(id: Int): RelativeLayout {
        return findViewById(id)

    }

    /**
     * this method returns RecyclerView id using  findViewById(id)
     */
    fun View.recyclerView(id: Int): RecyclerView {
        return findViewById(id)

    }

    /**
     * Using this function sets the error message on editText fields this function is using in @isValidate() function
     * @param errorMsg - pass String value as parameter in it.
     */
    private fun EditText.setFieldError(errorMsg: String? = null) {
        return if (errorMsg.isNullOrEmpty()) {
            this.setError("Required")
        } else {
            this.setError(errorMsg)
        }

    }

    private val getValidateList = arrayListOf<Boolean>()

    /**
     * This method checks EditText Fields is empty or not
     *
     * @param id pass the EditText id
     * @param errorMsg String error message it is optional default value is "Required"
     * @param isMobile boolean value for mobile number
     * @param mobileNumberLength default length is 10 pass int length
     *
     */
    fun isValidate(
        id: EditText,
        errorMsg: String? = null,
        isMobile: Boolean? = false,
        mobileNumberLength: Int = 10
    ) = apply {
        var isValidatedField = false
        when {
            id.text.isNullOrEmpty() -> {
                id.setFieldError(errorMsg)
            }
            isMobile == true -> {
                isValidatedField = if (id.length() < mobileNumberLength) {
                    false
                } else id.length() <= mobileNumberLength

            }

            else -> {
                isValidatedField = true
            }
        }
        getValidateList.add(isValidatedField)
        getValidateList.forEach {
            if (!it) isValidatedField = it
        }

    }

    /**
     * This fun returns Boolean if value is True Means all the fields are valid if return false means any one or all the fields are empty.
     */
    fun getValidatedFields(): Boolean {
        var isValidatedField = true
        getValidateList.forEach {
            if (it) {

            } else {
                isValidatedField = it
            }
        }
        getValidateList.clear()
        return isValidatedField
    }

    fun startAnimation(
        view: View,
        startValue: Float = 0f,
        endValue: Float = 1f,
        animationDuration: Long = 1500,
        animatorListener: (ValueAnimator?) -> Unit
    ) {
        val valueAnimator = ValueAnimator.ofFloat(startValue, endValue)
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            view.scaleX = value
            view.scaleY = value

        }
        valueAnimator.interpolator = BounceInterpolator()
        valueAnimator.duration = animationDuration
        // Set animator listener.
        animatorListener(valueAnimator)
        /*valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {

                // Navigate to main activity on navigation end.
            }

            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationStart(p0: Animator?) {}
        })*/
        // Start animation.
        valueAnimator.start()
    }

    fun crossFadeHideShow(viewToShow: View, viewToHide: View) {
        viewToShow.apply {
            alpha = 0f
            isVisible = true
            animate().alpha(1f).setDuration(500L).setListener(null)
        }
        viewToHide.animate().alpha(0f).setDuration(500)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    viewToHide.isVisible = false
                }
            })
    }

    fun extractURL(
        str: String
    ): MutableSet<String> {

        // Creating an empty ArrayList
        val list: MutableList<String> = java.util.ArrayList()

        // Regular Expression to extract
        // URL from the string
        val regex = ("\\b((?:https?|ftp|file):"
                + "//[-a-zA-Z0-9+&@#/%?="
                + "~_|!:, .;]*[-a-zA-Z0-9+"
                + "&@#/%=~_|])")

        // Compile the Regular Expression
        val p = Pattern.compile(
            regex,
            Pattern.CASE_INSENSITIVE
        )

        // Find the match between string
        // and the regular expression
        val m = p.matcher(str)

        // Find the next subsequence of
        // the input subsequence that
        // find the pattern
        while (m.find()) {

            // Find the substring from the
            // first index of match result
            // to the last index of match
            // result and add in the list
            list.add(
                str.substring(
                    m.start(0), m.end(0)
                )
            )
        }


        return list.toMutableSet()
    }

    fun getDateAgoTime(dateFormat: String, date: String?): String? {
        var finalInput: String? = null
        if (date?.isNotEmpty() == true) {
            val format = SimpleDateFormat(dateFormat)
            val past = format.parse(date)
            val now = Date()
            val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
            val hours = TimeUnit.MILLISECONDS.toHours(now.time - past.time)
            val days = TimeUnit.MILLISECONDS.toDays(now.time - past.time)
            if (seconds < 60) {
                finalInput = seconds.toString() + " Seconds ago"
            } else if (minutes < 60) {
                finalInput = minutes.toString() + " Minutes ago"
            } else if (hours < 24) {
                finalInput = hours.toString() + " Hours ago"
            } else {
                if (days.toInt() == 1) {
                    finalInput = days.toString() + " Day ago"
                } else {
                    finalInput = days.toString() + " Days ago"
                }
            }
        }

        return finalInput
    }

    fun getMilliSecondsToStringDate(milliSeconds: Long?): String? {
        val formatter: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        val calendar: Calendar? = Calendar.getInstance()
        calendar?.timeInMillis = milliSeconds ?: 599164200000
        return formatter.format(calendar?.time)
    }

    fun getDayAndFullMonthName(stringDate: String?): String? {
        var returnValue: String? = null
        val inputFormat = SimpleDateFormat("dd/MM/yyyy")
        val date = inputFormat.parse(stringDate)
        val calendarDate = Calendar.getInstance()
        calendarDate.time = date
        val dateTimeForm = SimpleDateFormat("dd\nMMMM")
        returnValue = dateTimeForm.format(date)
        return  returnValue
    }

}