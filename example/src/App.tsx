import * as React from 'react';
import { StyleSheet, View, Button } from 'react-native';
import LiveChat from '@ahiho/react-native-livechat';

export default function App() {
  React.useEffect(() => {
    LiveChat.initialize('12150039');
  }, []);

  return (
    <View style={styles.container}>
      <Button title="show LiveChat" onPress={() => LiveChat.presentChat()} />
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
