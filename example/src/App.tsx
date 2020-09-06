import React from 'react';
import { StyleSheet, View, Button } from 'react-native';
import { useLiveChat } from '@ahiho/react-native-livechat';

export default function App() {
  const liveChat = useLiveChat();

  React.useEffect(() => {
    liveChat.initialize('12150039');
  }, []);

  return (
    <View style={styles.container}>
      <Button title="show LiveChat" onPress={() => liveChat.presentChat()} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
