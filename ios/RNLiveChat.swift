import LiveChat

@objc(RNLiveChat)
class RNLiveChat: NSObject {

    @objc(initialize:)
    func initialize(licenseId: String) -> Void {
        DispatchQueue.main.async {
            LiveChat.licenseId = licenseId
        }
    }
    
    @objc(setGroup:)
    func setGroup(groupId: String) -> Void {
        DispatchQueue.main.sync {
            LiveChat.groupId = groupId
        }
    }
    
    @objc(presentChat)
    func presentChat() -> Void {
        DispatchQueue.main.async {
            LiveChat.presentChat()
        }
    }
    
    @objc(setCustomer:email:)
    func setCustomer(name: String, email: String) -> Void {
        DispatchQueue.main.async {
            LiveChat.name = name
            LiveChat.email = email
        }
    }
    
    @objc(setVariable:value:)
    func setVariable(key: String, value: String) -> Void {
        DispatchQueue.main.async {
            LiveChat.setVariable(withKey: key, value: value)
        }
    }
}
