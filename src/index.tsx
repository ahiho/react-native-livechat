import { NativeModules } from 'react-native';

type RNLiveChatType = {
  initialize(license: string): void;
  setGroup(groupId: string): void;
  presentChat(): void;
  setCustomer(name: string, email: string): void;
  setVariable(key: string, value: string): void;
};

const { RNLiveChat } = NativeModules;

export default RNLiveChat as RNLiveChatType;
