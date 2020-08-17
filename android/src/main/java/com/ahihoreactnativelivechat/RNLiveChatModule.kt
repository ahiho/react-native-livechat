package com.ahihoreactnativelivechat

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.annotation.Nullable
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.livechatinc.inappchat.ChatWindowConfiguration
import com.livechatinc.inappchat.ChatWindowView
import com.livechatinc.inappchat.models.NewMessageModel


class RNLiveChatModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext), ActivityEventListener, ChatWindowView.ChatWindowEventsListener {
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
        fullScreenChatWindowView.setUpListener(this)
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

  override fun onNewIntent(intent: Intent?) {

  }

  override fun onActivityResult(activity: Activity?, requestCode: Int, resultCode: Int, data: Intent?) {
    fullScreenChatWindowView.onActivityResult(requestCode, resultCode, data);
  }

  override fun onNewMessage(message: NewMessageModel?, windowVisible: Boolean) {
    if (message != null) {
      val map: WritableMap = WritableNativeMap()
      map.putString("messageType", message.messageType)
      map.putString("text", message.text)
      map.putString("id", message.id)
      map.putString("timestamp", message.timestamp);
      map.putString("author", message.author.name);
      map.putBoolean("windowVisible", windowVisible);
      sendEvent(EVENT_ON_NEW_MESSAGE, map)
    }
  }

  override fun onStartFilePickerActivity(intent: Intent?, requestCode: Int) {
    sendEvent(EVENT_ON_START_FILE_PICKER, null)
  }

  override fun handleUri(uri: Uri?): Boolean {
    val map: WritableMap = WritableNativeMap()
    map.putString("uri", uri.toString())
    sendEvent(EVENT_ON_HANDLE_URL, map)
    return true
  }

  override fun onChatWindowVisibilityChanged(visible: Boolean) {
    val map: WritableMap = WritableNativeMap()
    map.putBoolean("visible", visible)
    sendEvent(EVENT_ON_CHAT_WINDOW_VISIBILITY_CHANGED, map)
  }

  private fun initChatWindow(configuration: ChatWindowConfiguration) {
    fullScreenChatWindowView.setUpWindow(configuration)
    fullScreenChatWindowView.initialize()
  }

  private fun sendEvent(eventName: String, @Nullable params: WritableMap?) {
    reactApplicationContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java).emit(eventName, params)
  }

  companion object {
    const val EVENT_ON_NEW_MESSAGE = "onNewMessage"
    const val EVENT_ON_START_FILE_PICKER = "onStartFilePicker"
    const val EVENT_ON_HANDLE_URL = "onHandleUrl"
    const val EVENT_ON_CHAT_WINDOW_VISIBILITY_CHANGED = "onChatWindowVisibilityChanged"
  }
}
