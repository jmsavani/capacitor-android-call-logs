import Foundation

@objc public class CapacitorCallLogs: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
