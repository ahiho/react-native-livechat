import { NativeModules, BackHandler, NativeEventEmitter } from 'react-native';
import { useState, useEffect } from 'react';
const { RNLiveChat } = NativeModules;

const emitter = new NativeEventEmitter(RNLiveChat);

type RNLiveChatType = {
  initialize(license: string): void;
  setGroup(groupId: string): void;
  presentChat(): void;
  setCustomer(name: string, email: string): void;
  setVariable(key: string, value: string): void;
  // Only available on Android
  hideChat(): void;
};

export const useLiveChat = (): RNLiveChatType => {
  const [liveChatShowed, setLiveChatShowed] = useState<boolean>(false);

  const presentChat = () => {
    RNLiveChat.presentChat();
  };

  useEffect(() => {
    const onHardwareBackButtonPress = () => {
      if (liveChatShowed) {
        RNLiveChat.hideChat();
        return true;
      } else {
        return null;
      }
    };

    const backHandlerListener = BackHandler.addEventListener(
      'hardwareBackPress',
      onHardwareBackButtonPress
    );

    const handleLiveChatVisibilityChanged = emitter.addListener(
      'onChatWindowVisibilityChanged',
      ({ visible }) => {
        setLiveChatShowed(visible);
      }
    );

    return () => {
      backHandlerListener.remove();
      handleLiveChatVisibilityChanged.remove();
    };
  }, [liveChatShowed]);

  return {
    ...RNLiveChat,
    presentChat,
  } as RNLiveChatType;
};

export default RNLiveChat as RNLiveChatType;
