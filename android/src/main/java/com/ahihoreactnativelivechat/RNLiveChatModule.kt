package com.ahihoreactnativelivechat

import android.app.Activity
import android.content.Intent
import com.facebook.react.bridge.ActivityEventListener
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.livechatinc.inappchat.ChatWindowConfiguration
import com.livechatinc.inappchat.ChatWindowView


class RNLiveChatModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext), ActivityEventListener {
  var mContext = reactContext;
  var customVariables: HashMap<String, String> = HashMap()
  lateinit var license: String
  lateinit var groupId: String
  lateinit var visitorName: String
  lateinit var visitorEmail: String
  lateinit var fullScreenChatWindowView: ChatWindowView

  override fun getName(): String {
    return "RNLiveChat"
  }

  @ReactMethod
  fun initialize(license: String) {
    Thread(Runnable {
      mContext.currentActivity?.runOnUiThread(Runnable{
        this.license = license
        val configuration = ChatWindowConfiguration(
          license,
          null,
          null,
          null,
          null
        )
        fullScreenChatWindowView = ChatWindowView.createAndAttachChatWindowInstance(mContext.currentActivity!!);
        initChatWindow(configuration)
      })
    }).start()
  }

  @ReactMethod
  fun setGroup(groupId: String) {
    Thread(Runnable {
      mContext.currentActivity?.runOnUiThread(Runnable{
        this.groupId = groupId
        val configuration = ChatWindowConfiguration(
          this.license,
          groupId,
          this.visitorName,
          this.visitorEmail,
          this.customVariables
        )
        initChatWindow(configuration)
      })
    }).start()
  }

  @ReactMethod
  fun setCustomer(name: String, email: String) {
    Thread(Runnable {
      mContext.currentActivity?.runOnUiThread(Runnable{
        this.visitorName = name
        this.visitorEmail = email
        val configuration = ChatWindowConfiguration(
          this.license,
          this.groupId,
          this.visitorName,
          this.visitorEmail,
          this.customVariables
        )
        initChatWindow(configuration)
      })
    }).start()
  }

  @ReactMethod
  fun setVariable(key: String, value: String) {
    Thread(Runnable {
      mContext.currentActivity?.runOnUiThread(Runnable{
        this.customVariables.set(key, value)
        val configuration = ChatWindowConfiguration(
          this.license,
          this.groupId,
          this.visitorName,
          this.visitorEmail,
          this.customVariables
        )
        initChatWindow(configuration)
      })
    }).start()
  }

  @ReactMethod
  fun presentChat() {
    Thread(Runnable {
      mContext.currentActivity?.runOnUiThread(Runnable{
        fullScreenChatWindowView.showChatWindow()
      })
    }).start()
  }

  private fun initChatWindow(configuration: ChatWindowConfiguration) {
    fullScreenChatWindowView.setUpWindow(configuration)
    fullScreenChatWindowView.initialize()
  }

  override fun onNewIntent(intent: Intent?) {

  }

  override fun onActivityResult(activity: Activity?, requestCode: Int, resultCode: Int, data: Intent?) {
    fullScreenChatWindowView.onActivityResult(requestCode, resultCode, data);
  }
}
