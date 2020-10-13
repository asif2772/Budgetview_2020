package auth

import bv.UserLog
import grails.gorm.transactions.Transactional

@Transactional
class InitService {

    def tempUserData () {
        def user = new User()
        user.username = 'lmtest1'
        user.password = 'lmtest1'
        user.businessCompanyId = 6
        user.email = 'asif@impelitsolutions.com'
        user.firstName = 'asif'
        user.lastName = 'talukder'
        user.enabled = true
        user.accountExpired = false
        user.accountLocked = false
        user.passwordExpired =false
        user.save(flush: true)
        return
    }
    def saveUserLog (def authIpAddress,def authLogStr, def now, def authUserId) {

        def userLog = new UserLog()
        userLog.ipAddress = authIpAddress
        userLog.logInfo = authLogStr
        userLog.logTime = now
        userLog.userId = authUserId
        userLog.save(flush: true)
        return
    }

    /*def saveUserRole() {
        def userRole = new Role(authority: "ROLE_USER")
        userRole.save(flush: true)
        return
    }
    def saveTaxInit () {
        new TaxCategory(categoryName: "Tax 5.0", rate: 5, salesGlCode: '1200', status: 1).save()
        return
    }
    def saveVatCatInit () {
        new VatCategory(categoryName: 'VAT High', purchaseGlAccount:'2105', rate: 21, salesGlAccount:'2100', status: 1).save()
        new VatCategory(categoryName: 'VAT low', purchaseGlAccount:'2115', rate: 6, salesGlAccount:'2110', status: 1).save()
        new VatCategory(categoryName: 'No VAT', purchaseGlAccount:'2115', rate: 0, salesGlAccount:'2110', status: 1).save()
        return
    }
    def saveSysPrefixInit () {
        new factoring.SystemPrefix(id: 1, prefix: 'CUS', prefixLen: 3, title: 'Customer').save()
        new factoring.SystemPrefix(id: 2, prefix: 'VEN', prefixLen: 5, title: 'Vendor').save()
        new factoring.SystemPrefix(id: 3, prefix: 'PRO', prefixLen: 5, title: 'Product').save()
        new factoring.SystemPrefix(id: 4, prefix: 'J', prefixLen: 8, title: 'Journal Entry').save()
        new factoring.SystemPrefix(id: 5, prefix: 'Q', prefixLen: 6, title: 'Quick Entry').save()
        new factoring.SystemPrefix(id: 6, prefix: 'EXP', prefixLen: 6, title: 'Budget Expense').save()
        new factoring.SystemPrefix(id: 7, prefix: 'INVE', prefixLen: 6, title: 'Invoice Expense').save()
        new factoring.SystemPrefix(id: 8, prefix: 'INVI', prefixLen: 6, title: 'Invoice Income').save()
        new factoring.SystemPrefix(id: 9, prefix: 'INVEST', prefixLen: 6, title: 'Investment Invoice').save()
        new factoring.SystemPrefix(id: 10, prefix: 'INB', prefixLen: 6, title: 'Internal Banking').save()
        new factoring.SystemPrefix(id: 11, prefix: 'INC', prefixLen: 6, title: 'Budget Income').save()
        new factoring.SystemPrefix(id: 12, prefix: 'RE', prefixLen: 6, title: 'Receipt Entry').save()
        return
    }

    def saveUserLog (def authIpAddress,def authLogStr, def now, def authUserId) {

        def userLog = new UserLog()
            userLog.ipAddress = authIpAddress
            userLog.logInfo = authLogStr
            userLog.logTime = now
            userLog.userId = authUserId
            userLog.save(flush: true)
        return
    }*/

}
