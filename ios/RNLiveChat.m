#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(RNLiveChat, NSObject)

RCT_EXTERN_METHOD(initialize:(NSString*)licenseId)

RCT_EXTERN_METHOD(setGroup:(NSString*)groupId)

RCT_EXTERN_METHOD(presentChat)

RCT_EXTERN_METHOD(setCustomer:(NSString*)name email:(NSString*)email)

RCT_EXTERN_METHOD(setVariable:(NSString*)key value:(NSString*)value)

@end
