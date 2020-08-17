import { NativeModules } from 'react-native';

type ReactNativeLivechatType = {
  multiply(a: number, b: number): Promise<number>;
};

const { ReactNativeLivechat } = NativeModules;

export default ReactNativeLivechat as ReactNativeLivechatType;
