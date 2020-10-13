package bv

import org.grails.plugins.web.taglib.ValidationTagLib
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.servlet.support.RequestContextUtils

import java.text.DateFormat
import java.text.SimpleDateFormat

class CoreParamsHelper {
    ReconciliationService reconciliationService;

    def CoreParamsDropDown(String varName, String returnIndex, String selectionVal) {
        /*
       * varName: Variable Name of the core_param Table Ex: INVOICE_FREQUENCE
       * returnIndex: Return field name/id of the selection drop down Ex:invoice_frequency
       * selectionVal: Selection value Ex: weekly
       * GSP usage: <%= "${new CoreParamsHelper().CoreParamsDropDown('INVOICE_FREQUENCE','invoice_frequency','weekly')}" %>
       * */
        //String DataInstance = CoreParams.findByVarName(varName)
        def DataInstanceArr = new BudgetViewDatabaseService().executeQuery("SELECT paramsName FROM CoreParams where var_name='${varName}'")
        String DataInstance = DataInstanceArr[0][0]

        def firstArr = DataInstance.split("::")
        String valArr
        Integer indexPos
        String strIndex
        Integer varLength

        String strValue
        String dropdown = "<select id='" + returnIndex + "' name='" + returnIndex + "'tabindex=\"7\">"
        firstArr.each { phn ->
            indexPos = phn.lastIndexOf('{');
            strIndex = phn.substring(0, indexPos)
            varLength = phn.length();
            strValue = phn.substring(indexPos + 2, varLength - 2)
            if (strValue == selectionVal) {
                dropdown += "<option value='" + strValue + "' selected='selected'>" + strIndex + "</option>"
            } else {
                dropdown += "<option value='" + strValue + "'>" + strIndex + "</option>"
            }
        }
        dropdown += "</select>"
        return dropdown
    }

    def showBudgetDetailsSelectionType(returnIndex, selectionVal) {

        ArrayList typeArr = new ArrayList()
        def g = new ValidationTagLib()
        typeArr = [
                ['nw', g.message(code: 'dashboard.customerWise.label')],
                ['ahw', g.message(code: 'dashboard.accountHeadWise.label')]
        ]

        String dropDown = "<select name='" + returnIndex + "'>"

        for (int i = 0; i < typeArr.size(); i++) {
            if (typeArr[i][0].toString() == selectionVal) {
                dropDown += "<option value='" + typeArr[i][0] + "' selected='selected'>" + typeArr[i][1] + "</option>"
            } else {
                dropDown += "<option value='" + typeArr[i][0] + "'>" + typeArr[i][1] + "</option>"
            }
        }

        dropDown += "</select>"
        return dropDown

    }

    def CustomerTypeDropDown(returnIndex, selectionVal, isNull) {

        ArrayList mapStatuseArr = new ArrayList()
        def g = new ValidationTagLib()
        mapStatuseArr = [
//               ['0', 'Select Status'],
['bn', g.message(code: 'customerMaster.changingCustomer.dropdown')],
['cn', g.message(code: 'customerMaster.fixedCustomer.dropdown')],]

        String dropDown = "<select name='" + returnIndex + "'tabindex=\"7\">"

        for (int i = 0; i < mapStatuseArr.size(); i++) {
            if (mapStatuseArr[i][0].toString() == selectionVal) {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "' selected='selected'>" + mapStatuseArr[i][1] + "</option>"
            } else {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "'>" + mapStatuseArr[i][1] + "</option>"
            }
        }

        dropDown += "</select>"
        return dropDown

    }

    //for fiscal year status

    def FiscalYearStatusDropDown(returnIndex, selectionVal, isNull) {

        ArrayList mapStatuseArr = new ArrayList()
        def g = new ValidationTagLib()
        mapStatuseArr = [
                ['2', 'Inactive'],
                ['1', 'Active']
        ]

        String dropDown = "<select onchange='checkActiveFiscalYearType(this.value);' id=\"status12\" name='" + returnIndex + "' >"

        for (int i = 0; i < mapStatuseArr.size(); i++) {
            if (mapStatuseArr[i][0].toString() == selectionVal) {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "' selected='selected'>" + mapStatuseArr[i][1] + "</option>"
            } else {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "'>" + mapStatuseArr[i][1] + "</option>"
            }
        }

        dropDown += "</select>"
        return dropDown

    }

    //......SHR.........................................................

    def PageSizeDropDown(returnIndex, selectionVal, isNull) {
        /*
       * varName: Variable Name of the core_param Table Ex: INVOICE_FREQUENCE
       * returnIndex: Return field name/id of the selection drop down Ex:invoice_frequency
       * selectionVal: Selection value Ex: weekly
       * GSP usage: <%= "${new CoreParamsHelper().CoreParamsDropDown('INVOICE_FREQUENCE','invoice_frequency','weekly')}" %>
       * */
        //String DataInstance = CoreParams.findByVarName(varName)

        ArrayList mapStatuseArr = new ArrayList()
        def g = new ValidationTagLib()
        mapStatuseArr = [
//               ['0', 'Select Status'],
['letter', g.message(code: 'companySetup.defaultSetting.letter.dropdown')],
['a4', g.message(code: 'companySetup.defaultSetting.a4.dropdown')],]

        String dropDown = "<select name='" + returnIndex + "' tabindex=\"13\">"

        for (int i = 0; i < mapStatuseArr.size(); i++) {
            if (mapStatuseArr[i][0].toString() == selectionVal) {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "' selected='selected'>" + mapStatuseArr[i][1] + "</option>"
            } else {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "'>" + mapStatuseArr[i][1] + "</option>"
            }
        }

        dropDown += "</select>"
        return dropDown

    }

    def CoreParamsDropDownNotSnVendorType(String varName, String returnIndex, String selectionVal) {
        /*
       * varName: Variable Name of the core_param Table Ex: INVOICE_FREQUENCE
       * returnIndex: Return field name/id of the selection drop down Ex:invoice_frequency
       * selectionVal: Selection value Ex: weekly
       * GSP usage: <%= "${new CoreParamsHelper().CoreParamsDropDown('INVOICE_FREQUENCE','invoice_frequency','weekly')}" %>
       * */
        //String DataInstance = CoreParams.findByVarName(varName)
        def DataInstanceArr = new BudgetViewDatabaseService().executeQuery("SELECT paramsName FROM CoreParams where var_name='${varName}'")
        String DataInstance = DataInstanceArr[0][0]

        def firstArr = DataInstance.split("::")
        String valArr
        Integer indexPos
        String strIndex
        Integer varLength

        String strValue
        String dropdown = "<select id='" + returnIndex + "' name='" + returnIndex + "' tabindex=\"7\">"
        firstArr.each { phn ->
            indexPos = phn.lastIndexOf('{');
            strIndex = phn.substring(0, indexPos)
            varLength = phn.length();
            strValue = phn.substring(indexPos + 2, varLength - 2)
            if ((strValue != 'sn') && (strValue != 'rp')) {
                if (strValue == selectionVal) {
                    dropdown += "<option value='" + strValue + "' selected='selected'>" + strIndex + "</option>"
                } else {
                    dropdown += "<option value='" + strValue + "'>" + strIndex + "</option>"
                }
            }
        }
        dropdown += "</select>"
        return dropdown
    }

    def CoreParamsDropDownShopType(String varName, String returnIndex, String selectionVal) {
        /*
       * varName: Variable Name of the core_param Table Ex: INVOICE_FREQUENCE
       * returnIndex: Return field name/id of the selection drop down Ex:invoice_frequency
       * selectionVal: Selection value Ex: weekly
       * GSP usage: <%= "${new CoreParamsHelper().CoreParamsDropDown('INVOICE_FREQUENCE','invoice_frequency','weekly')}" %>
       * */
        //String DataInstance = CoreParams.findByVarName(varName)
        def DataInstanceArr = new BudgetViewDatabaseService().executeQuery("SELECT paramsName FROM CoreParams where var_name='${varName}'")
        String DataInstance = DataInstanceArr[0][0]
        def firstArr = DataInstance.split("::")
        String valArr
        Integer indexPos
        String strIndex
        Integer varLength

        String strValue
        String dropdown = "<select id='" + returnIndex + "' name='" + returnIndex + "' tabindex=\"7\">"
        firstArr.each { phn ->
            indexPos = phn.lastIndexOf('{');
            strIndex = phn.substring(0, indexPos)
            varLength = phn.length();
            strValue = phn.substring(indexPos + 2, varLength - 2)
            if (strValue == 'sn') {
                if (strValue == selectionVal) {
                    dropdown += "<option value='" + strValue + "' selected='selected'>" + strIndex + "</option>"
                } else {
                    dropdown += "<option value='" + strValue + "'>" + strIndex + "</option>"
                }
            }
        }
        dropdown += "</select>"
        return dropdown
    }

    def CoreParamsDropDownVendorType(String varName, String returnIndex, String selectionVal) {
        /*
       * varName: Variable Name of the core_param Table Ex: INVOICE_FREQUENCE
       * returnIndex: Return field name/id of the selection drop down Ex:invoice_frequency
       * selectionVal: Selection value Ex: weekly
       * GSP usage: <%= "${new CoreParamsHelper().CoreParamsDropDown('INVOICE_FREQUENCE','invoice_frequency','weekly')}" %>
       * */
        //String DataInstance = CoreParams.findByVarName(varName)
        def DataInstanceArr = new BudgetViewDatabaseService().executeQuery("SELECT paramsName FROM CoreParams where var_name='${varName}'")
        String DataInstance = DataInstanceArr[0][0]
        def firstArr = DataInstance.split("::")
        String valArr
        Integer indexPos
        String strIndex
        Integer varLength

        String strValue
        String dropdown = "<select id='" + returnIndex + "' name='" + returnIndex + "' tabindex=\"7\">"
        firstArr.each { phn ->
            indexPos = phn.lastIndexOf('{');
            strIndex = phn.substring(0, indexPos)
            varLength = phn.length();
            strValue = phn.substring(indexPos + 2, varLength - 2)
            if (strValue == 'vn') {
                if (strValue == selectionVal) {
                    dropdown += "<option value='" + strValue + "' selected='selected'>" + strIndex + "</option>"
                } else {
                    dropdown += "<option value='" + strValue + "'>" + strIndex + "</option>"
                }
            }
        }
        dropdown += "</select>"
        return dropdown
    }


    def StatusDropDown(returnIndex, selectionVal, isNull) {
        ArrayList mapStatuseArr = new ArrayList()
        def g = new ValidationTagLib()
        mapStatuseArr = [
//                ['0', 'Select Status'],
['1', g.message(code: 'coreParamsTagLib.active.label')],
['2', g.message(code: 'coreParamsTagLib.inactive.label')],]

        String dropDown = "<select name='" + returnIndex + "' tabindex=\"2\">"

        for (int i = 0; i < mapStatuseArr.size(); i++) {
            String paramStatus = selectionVal + "";
            //println("paramStatus "+paramStatus);
            if (mapStatuseArr[i][0].toString() == paramStatus) {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "' selected='selected'>" + mapStatuseArr[i][1] + "</option>"
            } else {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "'>" + mapStatuseArr[i][1] + "</option>"
            }
        }

        dropDown += "</select>"
        return dropDown

    }

    def ShowStatus(selectionVal) {
        /*
        * selectionVal: Show label name of Active/Inactive
        * GSP usage: <%= "${new CoreParamsHelper().ShowStatus(1)}" %>
        * */
        def showVal
        def g = new ValidationTagLib()
        if (selectionVal == 1) {
            showVal = g.message(code: 'coreParamsTagLib.active.label');
        } else if (selectionVal == 2) {
            showVal = g.message(code: 'coreParamsTagLib.inactive.label');
        } else if (selectionVal == -2) {
            showVal = g.message(code: 'default.button.delete.label');
        } else {
            showVal = "";
        }
        return showVal
    }

    def showCountryList(returnIndex, selectCountryId) {
        def listArr = new BudgetViewDatabaseService().executeQuery("SELECT id,printablename FROM countries")
        //println("Selected Val: "+selectValName)
        //println("listArr Val: "+listArr)
        String dropDown = "<select name='" + returnIndex + "' tabindex=\"7\">"
        if (listArr.size()) {
            for (int i = 0; i < listArr.size(); i++) {
                if (listArr[i][0].toString() == selectCountryId) {
                    //println("Index Val: " + Integer.parseInt(listArr[i][0].toString()))
                    dropDown += "<option value='" + listArr[i][0] + "' selected='selected'>" + listArr[i][1] + "</option>"
                } else {
                    dropDown += "<option value='" + listArr[i][0] + "'>" + listArr[i][1] + "</option>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def showCurrencyCode(selectCurrencyId) {
        def curCode = new BudgetViewDatabaseService().executeQuery("SELECT curr_code FROM currencies where id=" + selectCurrencyId)
        return curCode[0][0]
    }


    def IntegerDropDown(startVal, endVal, interVal, selectionVal, returnIndex, isNullSelect = '') {
        /*
        * startVal: Starting value Ex: 1
        * endVal: Ending value Ex: 10
        * interVal: Interval value Ex: 1
        * selectionVal: Selection value Ex: 5
        * returnIndex: Return field name/id of the selection drop down Ex:alertRepeatDays
        * isNullSelect: Default Selecting Sting. If null, it will consider as false. Ex: 'Select Alert Repeat Day(s)'
        * GSP usage: <%= "${new CoreParamsHelper().IntegerDropDown(1,30,1,0,'alertStartDays','Select Day(s) Start Alert')}" %>
        * */
        String dropDown = "<select name='" + returnIndex + "' tabindex=\"3\">"
        if (isNullSelect) {
            if (selectionVal == 0) {
                dropDown += "<option value='0' selected='selected'>" + isNullSelect + "</option>"
            } else {
                dropDown += "<option value='0'>" + isNullSelect + "</option>"
            }
        }
        def i = 0
        for (i = startVal; i <= endVal; i = i + interVal) {
            if (i == selectionVal) {
                dropDown += "<option value='" + i + "' selected='selected'>" + i + "</option>"
            } else {
                dropDown += "<option value='" + i + "'>" + i + "</option>"
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getDataFromTbl(domainName, tblName, valueFieldName, optionFieldName, returnIndex, selectValName, whereSting = '', isNullSelect = '') {

        /*
       * domainName: domain Object:bv.Currencies
       * tblName: Table Name for execute query: Currencies
       * valueFieldName: value field name of the table query: currCode
       * optionFieldName: Option field name of the table query: currency
       * returnIndex: Selection return name :currency_skb
       * selectValName: Default Selection value: EUR
       *  whereSting: Where condition of the query:status='1'
       * isNullSelect: Default Selecting Sting. If null, it will consider as false. Ex: 'Select Currency'
       * GSP usage: <%= "${new CoreParamsHelper().getDataFromTbl(bv.Currencies,'Currencies','currCode','currency','currency_skb','EUR',"status='1'",'Select Currency')}" %>
       * */

        def where = ""
        if (whereSting) {
            where = " WHERE " + whereSting
        }
        def listArr = new BudgetViewDatabaseService().executeQuery("SELECT " + valueFieldName + " As valuename," + optionFieldName + " As optionname FROM " + tblName + where)

        String[] strValue

        String dropDown = "<select name='" + returnIndex + "'>"
        if (isNullSelect) {
            if (selectValName == '') {
                dropDown += "<option value='' selected='selected'>" + isNullSelect + "</option>"
            } else {
                dropDown += "<option value=''>" + isNullSelect + "</option>"
            }
        }
        if (listArr.size()) {
            for (int i = 0; i < listArr.size(); i++) {
                strValue = listArr[i];
                if (strValue[0] == selectValName) {
                    dropDown += "<option value='" + strValue[0] + "' selected='selected'>" + strValue[1] + "</option>"
                } else {
                    dropDown += "<option value='" + strValue[0] + "'>" + strValue[1] + "</option>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getListData(modelName, params, tblName) {

        def max = params.max ?: 10
        def offset = params.offset ?: 0

        def searchFieldArr = GrailsDomainBinder.getMapping(modelName).columns
        def whereStr = ""
        def viewInstanceList
        def totalCount
        if (searchFieldArr.size()) {
            int i = 1
            searchFieldArr.each { phn, key ->
                if (i >= searchFieldArr.size()) {
                    whereStr = " " + whereStr + phn + " LIKE '%" + params.searchQuery + "%' "
                } else {
                    whereStr = " " + whereStr + phn + " LIKE '%" + params.searchQuery + "%' OR "
                }
                i++;
            }
        }
        if (params.searchQuery) {
            viewInstanceList = modelName.findAll("from " + tblName + " where " + whereStr, [max: Integer.parseInt(max), offset: Integer.parseInt(offset)])
            totalCount = modelName.findAll("from " + tblName + " where " + whereStr)
        } else {
            viewInstanceList = modelName.findAll("from " + tblName, [max: Integer.parseInt(max), offset: Integer.parseInt(offset)])
            totalCount = modelName.findAll("from " + tblName)
        }
        return [list: viewInstanceList, count: totalCount.size()]
    }

    def getDefaultGLAccountDropDown(returnIndex, selectIndex) {

        def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,name As categoryName FROM ChartGroup where status=1")
        String dropDown = "<select name='" + returnIndex + "'>"

        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]
                def ProductArr
                ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,accountCode,accountName FROM bv.ChartMaster where status='1' AND chart_group_id='" + catId + "'")
                if (ProductArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < ProductArr.size(); j++) {

                        if (ProductArr[j][1] == selectIndex) {
                            dropDown += "<option value='" + ProductArr[j][1] + "' selected='selected'>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        } else {
                            dropDown += "<option value='" + ProductArr[j][1] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getDefaultGLAccountDropDownCustomer(returnIndex, selectIndex) {

        def ChartClassArr = new BudgetViewDatabaseService().executeQuery("SELECT id FROM ChartClass WHERE chartClassTypeId IN(4) AND status=1")
        def ChartClassString = ChartClassArr[0].join(",")

        def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,name As categoryName FROM ChartGroup where chartClassId IN(" + ChartClassString + ") AND status=1")
        String dropDown = "<select id='" + returnIndex + "' name='" + returnIndex + "' tabindex=\"10\">"

        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]
                def ProductArr
                ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,accountCode,accountName FROM bv.ChartMaster where status='1' AND chart_group_id='" + catId + "'")
                if (ProductArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < ProductArr.size(); j++) {

                        if (ProductArr[j][1] == selectIndex) {
                            dropDown += "<option value='" + ProductArr[j][1] + "' selected='selected'>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        } else {
                            dropDown += "<option value='" + ProductArr[j][1] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getChartClassTypeGLAccountDropDown(returnIndex, selectIndex) {

        def CC = new BudgetViewDatabaseService().executeQuery("SELECT id FROM bv.ChartClass WHERE chartClassTypeId = 5 OR chartClassTypeId = 6 OR chartClassTypeId = 7")

        def con = ''
        for (int p = 0; p < CC.size(); p++) {
            if (p == 0) {
                con += ' chartClassId=' + CC[p][0]
            } else {
                con += ' OR chartClassId=' + CC[p][0]
            }
        }

        println("Seedupriya" + CC)
        def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,name As categoryName FROM ChartGroup where status=1 AND " + con)
        String dropDown = "<select name='" + returnIndex + "'>"

        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]
                def ProductArr
                ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,accountCode,accountName FROM bv.ChartMaster where status='1' AND chart_group_id='" + catId + "'")
                if (ProductArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < ProductArr.size(); j++) {

                        if (ProductArr[j][1] == selectIndex) {
                            dropDown += "<option value='" + ProductArr[j][1] + "' selected='selected'>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        } else {
                            dropDown += "<option value='" + ProductArr[j][1] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getGeneratedBudgetExpanseCode() {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=6")
        def Prefix = PrefixDataArr[0][0]
        def PrefixLength = PrefixDataArr[0][1]
        ///////////////////MAX VENDOR ID/////
        def VendorDataArr = new BudgetViewDatabaseService().executeQuery("SELECT MAX(id) FROM bv.BudgetItemExpense")

        def newSequence
        if (VendorDataArr[0]) {
            newSequence = VendorDataArr[0] + 1
        } else {
            newSequence = 1
        }
        def VendorCode
        if (PrefixLength == 2) {
            VendorCode = String.format("%02d", newSequence)
        } else if (PrefixLength == 3) {
            VendorCode = String.format("%03d", newSequence)
        } else if (PrefixLength == 4) {
            VendorCode = String.format("%04d", newSequence)
        } else if (PrefixLength == 5) {
            VendorCode = String.format("%05d", newSequence)
        } else if (PrefixLength == 6) {
            VendorCode = String.format("%06d", newSequence)
        } else if (PrefixLength == 7) {
            VendorCode = String.format("%07d", newSequence)
        } else if (PrefixLength == 8) {
            VendorCode = String.format("%08d", newSequence)
        } else if (PrefixLength == 9) {
            VendorCode = String.format("%09d", newSequence)
        }

        return VendorCode
    }

    def getGeneratedBudgetIncomeCode() {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=11")
        def Prefix = PrefixDataArr[0][0]
        def PrefixLength = PrefixDataArr[0][1]
        ///////////////////MAX VENDOR ID/////
        def VendorDataArr = new BudgetViewDatabaseService().executeQuery("SELECT MAX(id) FROM bv.BudgetItemIncome")

        def newSequence
        if (VendorDataArr[0]) {
            newSequence = VendorDataArr[0] + 1
        } else {
            newSequence = 1
        }
        def VendorCode
        if (PrefixLength == 2) {
            VendorCode = String.format("%02d", newSequence)
        } else if (PrefixLength == 3) {
            VendorCode = String.format("%03d", newSequence)
        } else if (PrefixLength == 4) {
            VendorCode = String.format("%04d", newSequence)
        } else if (PrefixLength == 5) {
            VendorCode = String.format("%05d", newSequence)
        } else if (PrefixLength == 6) {
            VendorCode = String.format("%06d", newSequence)
        } else if (PrefixLength == 7) {
            VendorCode = String.format("%07d", newSequence)
        } else if (PrefixLength == 8) {
            VendorCode = String.format("%08d", newSequence)
        } else if (PrefixLength == 9) {
            VendorCode = String.format("%09d", newSequence)
        }

        return VendorCode
    }


    def getCurrencyDropDown(returnIndex, selectValName) {

        def listArr = new BudgetViewDatabaseService().executeQuery("SELECT id,currency,curr_symbol FROM Currencies where status=1")

        //println("Selected Val: "+selectValName)
        //println("listArr Val: "+listArr)
        String dropDown = "<select name='" + returnIndex + "' tabindex=\"10\">"
        if (listArr.size()) {
            for (int i = 0; i < listArr.size(); i++) {
                if (Integer.parseInt(listArr[i][0].toString()) == Integer.parseInt(selectValName.toString())) {
                    println("Index Val: " + Integer.parseInt(listArr[i][0].toString()))
                    dropDown += "<option value='" + listArr[i][0] + "' selected='selected'>" + listArr[i][1] + " [" + listArr[i][2] + "]" + "</option>"
                } else {
                    dropDown += "<option value='" + listArr[i][0] + "'>" + listArr[i][1] + " [" + listArr[i][2] + "]" + "</option>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getCountryDropDown(returnIndex, selectValName) {

        def listArr = new BudgetViewDatabaseService().executeQuery("SELECT id,printablename,iso3 FROM Countries where status=1")

        String dropDown = "<select name='" + returnIndex + "'>"
        if (listArr.size()) {
            for (int i = 0; i < listArr.size(); i++) {
                if (selectValName == 'null') {
                    dropDown += "<option value='" + listArr[i][0] + "'>" + listArr[i][1] + " [" + listArr[i][2] + "]" + "</option>"
                } else {
                    if (Integer.parseInt(listArr[i][0].toString()) == Integer.parseInt(selectValName.toString())) {
                        dropDown += "<option value='" + listArr[i][0] + "' selected='selected'>" + listArr[i][1] + " [" + listArr[i][2] + "]" + "</option>"
                    } else {
                        dropDown += "<option value='" + listArr[i][0] + "'>" + listArr[i][1] + " [" + listArr[i][2] + "]" + "</option>"
                    }
                }
            }
        }

        dropDown += "</select>"
        return dropDown
    }


    def getCountryPrintableDropDown(returnIndex, selectValName = "") {

        def listArr = new BudgetViewDatabaseService().executeQuery("SELECT id,printablename,iso3 FROM Countries where status=1")

        String dropDown = "<select name='" + returnIndex + "' tabindex=\"5\">"
        if (listArr.size()) {
            for (int i = 0; i < listArr.size(); i++) {
                if (listArr[i][1].toString() == selectValName.toString()) {
                    println(listArr[i][1])
                    dropDown += "<option value='" + listArr[i][1] + "' selected='selected'>" + listArr[i][1] + " [" + listArr[i][2] + "]" + "</option>"
                } else {
                    dropDown += "<option value='" + listArr[i][1] + "'>" + listArr[i][1] + " [" + listArr[i][2] + "]" + "</option>"
                }
            }
        } else {
            dropDown += "<option>" + g.message(code: 'coreParamsHelper.noCountry.label') + "</option>"
        }

        dropDown += "</select>"
        return dropDown
    }

    def getProductInformationForInvoice(productId) {
        def listArr = new BudgetViewDatabaseService().executeQuery("SELECT productCode,productName,description FROM ProductMaster WHERE id=" + productId)

        if (listArr.size() > 0) {
            return listArr[0]
        } else {
            return ["No Selected", "Product", ""]
        }

    }

    def getProductInformation(productId) {

        def listArr = new BudgetViewDatabaseService().executeQuery("SELECT productCode,productName,description FROM ProductMaster WHERE id=" + productId)
        //def listArr = ProductMaster.executeQuery("SELECT productCode,productName,description FROM ProductMaster WHERE id=" + productId)
        if (listArr.size() > 0) {
            return listArr[0]
        } else {
            return ["No Selected", "Product", ""]
        }
        //return listArr[0]
    }


    def getProductDropDown(returnIndex, selectIndex = '0') {

        def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,categoryName FROM ProductCategory where status=1")
        // String dropDown = "<select name='" + returnIndex + "' onchange=\"goToPage(this.value)\">"
        String dropDown = "<select id=\"productId\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'/bv/budgetItemExpenseDetails/selectProductRelatedInformation',success:function(data,textStatus){jQuery('#searchresults').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"productId\">"

        def productPrefix = showGeneratedProductCode();


        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]
                def ProductArr
                ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,productCode,productName,purchaseAccountCode FROM bv.ProductMaster where status='1' AND product_category_id='" + catId + "'")
                if (ProductArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < ProductArr.size(); j++) {
                        def productPurchaseAccountCode = ProductArr[j][3]
                        def CompanyBankGlRelationArr = new BudgetViewDatabaseService().executeQuery("SELECT id,bankAccountCode FROM bv.CompanyBankGlRelation where status='1' AND gl_chart_code='" + productPurchaseAccountCode + "'")
                        if (CompanyBankGlRelationArr.size()) {
                            if (ProductArr[j][0] == Integer.parseInt(selectIndex)) {
                                dropDown += "<option value='" + ProductArr[j][0] + "' selected='selected'>" + productPrefix + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                            } else {
                                dropDown += "<option value='" + ProductArr[j][0] + "' >" + productPrefix + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                            }
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getBudgetVATCategoryDropDown(returnIndex, selectIndex = 0, contextCustomer = "") {

        //def VatCatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,categoryName,rate FROM VatCategory WHERE status=1")
        LinkedHashMap gridResultVatCatArr
        String selectProductArr = "id,categoryName,rate"
        String selectIndexesProductArr = "id,categoryName,rate"
        String fromProductArr = "VatCategory"
        String whereProductArr = "status=1"
        String orderByProductArr = ""
        gridResultVatCatArr = new BudgetViewDatabaseService().select(selectProductArr, fromProductArr, whereProductArr, orderByProductArr, '', 'true', selectIndexesProductArr)

        def VatCatArr = gridResultVatCatArr['dataGridList']
        def g = new ValidationTagLib()

        //String dropDown = "<select name='" + returnIndex + "'  id='" + returnIndex + "'>"
        // String dropDown = "<select id=\"vatCategoryId\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BudgetItemExpenseDetails/selectVATRelatedInformation',success:function(data,textStatus){jQuery('#searchresults').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"vatCategoryId\">"
//        String dropDown = "<select id=\"vatCategoryId\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BudgetItemExpenseDetails/selectVATRelatedInformation',success:function(data,textStatus){jQuery('#searchresultsVAT').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"vatCategoryId\">"
        String dropDown = "<select id=\"vatCategoryId\" onchange=\"calculateVAT(this.value);\" name=\"vatCategoryId\">"

        if (VatCatArr.size()) {
            for (int i = 0; i < VatCatArr.size(); i++) {
//                if (VatCatArr[i][0] == Integer.parseInt(selectIndex)) {
                if (VatCatArr[i][0] == selectIndex) {

                    dropDown += "<option value='" + VatCatArr[i][0] + "' selected='selected'>" + VatCatArr[i][1] + " (" + VatCatArr[i][2] + "%)" + "</option>"
                } else {

                    dropDown += "<option value='" + VatCatArr[i][0] + "'>" + VatCatArr[i][1] + " (" + VatCatArr[i][2] + "%)" + "</option>"
                }
            }
        } else {
            dropDown += "<option>" + g.message(code: 'coreParamsHelper.noVatCategorySetup.label') + "</option>"
        }

        dropDown += "</select>"
        return dropDown

    }

    def getBudgetVATCategoryIncomeDropDown(returnIndex, selectIndex = 0, contextCustomer = "") {

        //println(" selectIndex: " + selectIndex)

        LinkedHashMap gridResultVatCatArr
        String selectProductArr = "id,categoryName,rate"
        String selectIndexesProductArr = "id,categoryName,rate"
        String fromProductArr = "VatCategory"
        String whereProductArr = "status=1"
        String orderByProductArr = ""
        gridResultVatCatArr = new BudgetViewDatabaseService().select(selectProductArr, fromProductArr, whereProductArr, orderByProductArr, '', 'true', selectIndexesProductArr)

        def VatCatArr = gridResultVatCatArr['dataGridList']

        //def VatCatArr = VatCategory.executeQuery("SELECT id,categoryName,rate FROM VatCategory WHERE status=1")
//        String dropDown = "<select id=\"vatCategoryId\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/budgetItemIncomeDetails/selectVATRelatedInformation',success:function(data,textStatus){jQuery('#searchresultsVAT').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"vatCategoryId\">"
        String dropDown = "<select id=\"vatCategoryId\" onchange=\"calculateVAT(this.value);\" name=\"vatCategoryId\">"

        if (VatCatArr.size()) {
            for (int i = 0; i < VatCatArr.size(); i++) {
//                if (VatCatArr[i][0] == Integer.parseInt(selectIndex)) {
                if (VatCatArr[i][0] == selectIndex) {

                    dropDown += "<option value='" + VatCatArr[i][0] + "' selected='selected'>" + VatCatArr[i][1] + " (" + VatCatArr[i][2] + "%)" + "</option>"
                } else {

                    dropDown += "<option value='" + VatCatArr[i][0] + "'>" + VatCatArr[i][1] + " (" + VatCatArr[i][2] + "%)" + "</option>"
                }
            }
        } else {
            dropDown += "<option>" + g.message(code: 'coreParamsHelper.noVatCategorySetup.label') + "</option>"
        }

        dropDown += "</select>"
        return dropDown
    }


    def getIncomeProductDropDown(returnIndex, selectIndex = '0') {

        def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,categoryName FROM ProductCategory where status=1")
        // String dropDown = "<select name='" + returnIndex + "' onchange=\"goToPage(this.value)\">"
        String dropDown = "<select id=\"productId\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'/bv/budgetItemIncomeDetails/selectProductRelatedInformation',success:function(data,textStatus){jQuery('#searchresults').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"productId\">"

        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]
                def ProductArr

                ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,productCode,productName,salesAccountCode FROM bv.ProductMaster where status='1' AND product_category_id='" + catId + "'")
                if (ProductArr.size()) {

                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < ProductArr.size(); j++) {

                        def productSalesAccountCode = ProductArr[j][3]
                        def CompanyBankGlRelationArr = new BudgetViewDatabaseService().executeQuery("SELECT id,bankAccountCode FROM bv.CompanyBankGlRelation where status='1' AND gl_chart_code='" + productSalesAccountCode + "'")
                        if (CompanyBankGlRelationArr.size()) {
                            if (ProductArr[j][0] == Integer.parseInt(selectIndex)) {
                                dropDown += "<option value='" + ProductArr[j][0] + "' selected='selected'>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                            } else {
                                dropDown += "<option value='" + ProductArr[j][0] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                            }
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }
    //////////////////
    def getEditProductDropDown(returnIndex, selectIndex = '0') {

        def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,categoryName FROM ProductCategory where status=1")
        // String dropDown = "<select name='" + returnIndex + "' onchange=\"goToPage(this.value)\">"
        String dropDown = "<select id=\"productId\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'/bv/budgetItemExpenseDetails/selectEditProductRelatedInformation',success:function(data,textStatus){jQuery('#searchEditresults').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"productId\">"

        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]
                def ProductArr
                ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,productCode,productName,purchaseAccountCode FROM bv.ProductMaster where status='1' AND product_category_id='" + catId + "'")
                if (ProductArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < ProductArr.size(); j++) {

                        def productPurchaseAccountCode = ProductArr[j][3]
                        def CompanyBankGlRelationArr = new BudgetViewDatabaseService().executeQuery("SELECT id,bankAccountCode FROM bv.CompanyBankGlRelation where status='1' AND gl_chart_code='" + productPurchaseAccountCode + "'")
                        if (CompanyBankGlRelationArr.size()) {
                            if (ProductArr[j][0] == Integer.parseInt(selectIndex)) {
                                dropDown += "<option value='" + ProductArr[j][0] + "' selected='selected'>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                            } else {
                                dropDown += "<option value='" + ProductArr[j][0] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                            }
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }
    ///////
    def getEditProductIncomeDropDown(returnIndex, selectIndex = '0') {

        def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,categoryName FROM ProductCategory where status=1")
        // String dropDown = "<select name='" + returnIndex + "' onchange=\"goToPage(this.value)\">"
        String dropDown = "<select id=\"productId\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'/bv/budgetItemIncomeDetails/selectEditProductRelatedInformation',success:function(data,textStatus){jQuery('#searchEditresults').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"productId\">"

        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]
                def ProductArr
                ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,productCode,productName,purchaseAccountCode FROM bv.ProductMaster where status='1' AND product_category_id='" + catId + "'")
                if (ProductArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < ProductArr.size(); j++) {
                        def productPurchaseAccountCode = ProductArr[j][3]
                        def CompanyBankGlRelationArr = new BudgetViewDatabaseService().executeQuery("SELECT id,bankAccountCode FROM bv.CompanyBankGlRelation where status='1' AND gl_chart_code='" + productPurchaseAccountCode + "'")
                        if (CompanyBankGlRelationArr.size()) {
                            if (ProductArr[j][0] == Integer.parseInt(selectIndex)) {
                                dropDown += "<option value='" + ProductArr[j][0] + "' selected='selected'>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                            } else {
                                dropDown += "<option value='" + ProductArr[j][0] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                            }
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    /*
    * This function is using the section of Income Invoice
    * */

    def getInvoiceIncomeProductDropDown(returnIndex, selectIndex = '0') {

        def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,categoryName FROM ProductCategory where status=1")
        def g = new ValidationTagLib()
        // String dropDown = "<select name='" + returnIndex + "' onchange='goToPage(this.value)'>"
        String dropDown = "<select id=\"productId\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'/bv/invoiceIncome/selectProductRelatedInformation',success:function(data,textStatus){jQuery('#searchresults').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"productId\">"
        dropDown += "<option value=\"0\">" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
//Please Select a Product

        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]
                def ProductArr
                ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,productCode,productName,salesAccountCode FROM bv.ProductMaster where status='1' AND (product_type='Purchase' OR product_type='Both') AND product_category_id='" + catId + "'")
                if (ProductArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < ProductArr.size(); j++) {

                        def productSalesAccountCode = ProductArr[j][3]
                        def CompanyBankGlRelationArr = new BudgetViewDatabaseService().executeQuery("SELECT id,bankAccountCode FROM bv.CompanyBankGlRelation where status='1' AND gl_chart_code='" + productSalesAccountCode + "'")
                        if (CompanyBankGlRelationArr.size()) {
                            if (ProductArr[j][0] == Integer.parseInt(selectIndex)) {
                                dropDown += "<option value='" + ProductArr[j][0] + "' selected='selected'>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                            } else {
                                dropDown += "<option value='" + ProductArr[j][0] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                            }
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    /*
    * This function is using the section of Expense Invoice
    * */

    def getExpenseInvoiceProductDropDown(returnIndex, selectIndex = '0') {

        def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,categoryName FROM ProductCategory where status=1")
        def g = new ValidationTagLib()
        // String dropDown = "<select name='" + returnIndex + "' onchange='goToPage(this.value)'>"
        String dropDown = "<select id=\"productId\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'/bv/invoiceIncome/selectProductRelatedInformation',success:function(data,textStatus){jQuery('#searchresults').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"productId\">"
        dropDown += "<option value=\"0\">" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
//Please Select a Product
        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]
                def ProductArr
                ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id, productCode, productName, salesAccountCode FROM bv.ProductMaster where status='1' AND (product_type='Purchase' OR product_type='Both') AND product_category_id='" + catId + "'")
                if (ProductArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < ProductArr.size(); j++) {

                        def productSalesAccountCode = ProductArr[j][3]
                        def CompanyBankGlRelationArr = new BudgetViewDatabaseService().executeQuery("SELECT id,bankAccountCode FROM bv.CompanyBankGlRelation where status='1' AND gl_chart_code='" + productSalesAccountCode + "'")
                        if (CompanyBankGlRelationArr.size()) {
                            if (ProductArr[j][0] == Integer.parseInt(selectIndex)) {
                                dropDown += "<option value='" + ProductArr[j][0] + "' selected='selected'>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                            } else {
                                dropDown += "<option value='" + ProductArr[j][0] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                            }
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getProductDefault(productId) {

        if (productId > 0) {
            def ProductArr = new BudgetViewDatabaseService().executeQuery("FROM ProductMaster AS pro  WHERE pro.id='" + productId + "'")
            return [taxrate: ProductArr.get(0).taxCategory.rate, taxname: ProductArr.get(0).taxCategory.categoryName, unitprice: ProductArr.get(0).actualCost]
        } else {
            return [taxrate: '0', taxname: '', unitprice: '0']
        }
    }

    def momentOfSendingInvoiceDetails(id) {
        def momentArr = new BudgetViewDatabaseService().executeQuery("From MomentOfSendingInvoice  where id=" + id)
        return momentArr
    }

    def setCompanyInformation() {
        def CompanyArr = CompanySetup.executeQuery("FROM CompanySetup where id=1")

        println("CompanyArr: " + CompanyArr)
        if (CompanyArr.size()) {
            return CompanyArr.get(0)
        } else {
            return [decimalSeprator: ".", thousandSeperator: ",", amountDecimalPoint: 2]
        }

        return [decimalSeprator: ".", thousandSeperator: ",", amountDecimalPoint: 2]
    }

    def monthNameShow(monthVal) {
        def monthName = ''
        if (monthVal == 1) {
            monthName = 'Jan'
        } else if (monthVal == 2) {
            monthName = 'Feb'
        } else if (monthVal == 3) {
            monthName = 'Mar'
        } else if (monthVal == 4) {
            monthName = 'Apr'
        } else if (monthVal == 5) {
            monthName = 'May'
        } else if (monthVal == 6) {
            monthName = 'Jun'
        } else if (monthVal == 7) {
            monthName = 'Jul'
        } else if (monthVal == 8) {
            monthName = 'Aug'
        } else if (monthVal == 9) {
            monthName = 'Sep'
        } else if (monthVal == 10) {
            monthName = 'Oct'
        } else if (monthVal == 11) {
            monthName = 'Nov'
        } else if (monthVal == 12) {
            monthName = 'Dec'
        }
        return monthName
    }

    def getExpanseBookPeriod(returnIndex, venID, selectIndex) {
        def fiscalID = 1
        venID = 1
        def budgetItemList = new BudgetViewDatabaseService().executeQuery("SELECT b.id,b.bookingPeriodStartMonth,b.bookingPeriodStartYear,b.bookingPeriodEndMonth,b.bookingPeriodEndYear FROM BudgetItemExpense AS b WHERE b.vendorId=" + venID + " AND b.fiscalId=" + fiscalID + "AND b.status=1")
        //new CoreParamsHelper().getMonthDropDown('bookingPeriod','',0)
        def g = new ValidationTagLib()
        String dropDown = "<select name='" + returnIndex + "'>"
        if (budgetItemList.size()) {

            for (int i = 0; i < budgetItemList.size(); i++) {

                if (budgetItemList[i][0] == selectIndex) {
                    dropDown += "<option value='" + budgetItemList[i][0] + "' selected='selected'>" + monthNameShow(budgetItemList[i][1]) + " " + budgetItemList[i][2] + " - " + monthNameShow(budgetItemList[i][3]) + " " + budgetItemList[i][4] + "</option>"
                } else {
                    dropDown += "<option value='" + budgetItemList[i][0] + "'>" + monthNameShow(budgetItemList[i][1]) + " " + budgetItemList[i][2] + " - " + monthNameShow(budgetItemList[i][3]) + " " + budgetItemList[i][4] + "</option>"
                }
            }
        } else {
            dropDown += "<option  value='0'>" + g.message(code: 'coreParamsHelper.noBudgetItemSetup.label') + "</option>"
        }
        dropDown += "</select>"
        return dropDown

    }

    def getIncomeBookPeriod(returnIndex, cusID, selectIndex) {
        def fiscalID = 1
        cusID = 1
        //def budgetItemList = BudgetItemIncome.executeQuery("SELECT b.id,DATE_FORMAT(b.bookingPeriodStart,'%b,%Y') AS bookStart ,DATE_FORMAT(b.bookingPeriodEnd,'%b,%Y') AS bookEnd FROM BudgetItemIncome AS b WHERE b.customerId="+cusID+" AND b.fiscalYearId=" + fiscalID + "AND b.status=1")
        def budgetItemList = new BudgetViewDatabaseService().executeQuery("SELECT b.id,b.bookingPeriodStartMonth,b.bookingPeriodStartYear,b.bookingPeriodEndMonth,b.bookingPeriodEndYear FROM BudgetItemIncome AS b WHERE b.customerId=" + cusID + " AND b.fiscalId=" + fiscalID + "AND b.status=1")
        //println(budgetItemList)
        def g = new ValidationTagLib()
        String dropDown = "<select name='" + returnIndex + "'>"
        if (budgetItemList.size()) {
            for (int i = 0; i < budgetItemList.size(); i++) {
                if (budgetItemList[i][0] == selectIndex) {
                    dropDown += "<option value='" + budgetItemList[i][0] + "' selected='selected'>" + monthNameShow(budgetItemList[i][1]) + " " + budgetItemList[i][2] + " - " + monthNameShow(budgetItemList[i][3]) + " " + budgetItemList[i][4] + "</option>"
                } else {
                    dropDown += "<option value='" + budgetItemList[i][0] + "'>" + monthNameShow(budgetItemList[i][1]) + " " + budgetItemList[i][2] + " - " + monthNameShow(budgetItemList[i][3]) + " " + budgetItemList[i][4] + "</option>"
                }
            }
        } else {
            dropDown += "<option value='0'>" + g.message(code: 'coreParamsHelper.noBudgetItemSetup.label') + "</option>"
        }
        dropDown += "</select>"
        return dropDown
    }

    def getVatCategory(returnIndex, selectIndex) {

        def VatCatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,categoryName,rate FROM VatCategory WHERE status=1")
        String dropDown = "<select class='styled sidebr01' name='" + returnIndex + "' id='" + returnIndex + "'tabindex=\"8\" >"
//        String dropDown = "<select class=\"styled sidebr01\" id=\"${returnIndex}\" onchange=\"onChangeBudgetDetails(this.value)\" name=\"${returnIndex}\">"

        if (VatCatArr.size()) {
            for (int i = 0; i < VatCatArr.size(); i++) {
                //println("VatCatArr[i][0]: "+VatCatArr[i][0])
                //if (VatCatArr[i][0] == Integer.parseInt(selectIndex)) {
                if (VatCatArr[i][0] == selectIndex) {
                    //if (Integer.parseInt(VatCatArr[i][0].toString())== Integer.parseInt(selectIndex.toString())) {
                    dropDown += "<option value='" + VatCatArr[i][0] + "' selected='selected'>" + VatCatArr[i][1] + " (" + VatCatArr[i][2] + "%)" + "</option>"
                } else {

                    dropDown += "<option value='" + VatCatArr[i][0] + "'>" + VatCatArr[i][1] + " (" + VatCatArr[i][2] + "%)" + "</option>"
                }
            }
        } else {
            dropDown += "<option>" + g.message(code: 'coreParamsHelper.noVatCategorySetup.label') + "</option>"
        }

        dropDown += "</select>"
        return dropDown
    }

    def getTAXCategory(returnIndex, selectIndex) {

        def VatCatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,categoryName,rate FROM taxCategory WHERE status=1")
        String dropDown = "<select name='" + returnIndex + "'  id='" + returnIndex + "'>"
        if (VatCatArr.size()) {
            for (int i = 0; i < VatCatArr.size(); i++) {
                //if (VatCatArr[i][0] == Integer.parseInt(selectIndex)) {
                if (VatCatArr[i][0] == selectIndex) {
                    //if (Integer.parseInt(VatCatArr[i][0].toString())== Integer.parseInt(selectIndex.toString())) {
                    dropDown += "<option value='" + VatCatArr[i][0] + "' selected='selected'>" + VatCatArr[i][1] + " (" + VatCatArr[i][2] + "%)" + "</option>"
                } else {

                    dropDown += "<option value='" + VatCatArr[i][0] + "'>" + VatCatArr[i][1] + " (" + VatCatArr[i][2] + "%)" + "</option>"
                }
            }
        } else {
            dropDown += "<option>" + g.message(code: 'coreParamsHelper.noVatCategorySetup.label') + "</option>"
        }

        dropDown += "</select>"
        return dropDown
    }

    def getMomentOfSendingInvoice(returnIndex, selectIndex) {

        def VatCatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,name FROM momentOfSendingInvoice WHERE status=1")
        String dropDown = "<select name='" + returnIndex + "'  id='" + returnIndex + "' tabindex=\"12\">"
        if (VatCatArr.size()) {
            for (int i = 0; i < VatCatArr.size(); i++) {
                if (VatCatArr[i][0] == selectIndex) {
                    //if (Integer.parseInt(VatCatArr[i][0].toString())== Integer.parseInt(selectIndex.toString())) {
                    dropDown += "<option value='" + VatCatArr[i][0] + "' selected='selected'>" + VatCatArr[i][1] + "</option>"
                } else {

                    dropDown += "<option value='" + VatCatArr[i][0] + "'>" + VatCatArr[i][1] + "</option>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getVatCategoryWithAjax(returnIndex, selectIndex = "", contextCustomer = "") {

        def VatCatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,categoryName,rate FROM VatCategory WHERE status=1")
        String dropDown = "";
        dropDown = "<select id=\"vatRate\" onchange=\"calPriceByVAT(this.value);\" name=\"vatRate\">"

        //println("selectIndex "+selectIndex);
        if (VatCatArr.size()) {
            for (int i = 0; i < VatCatArr.size(); i++) {
                String vatCat = VatCatArr[i][0]
                if (vatCat == selectIndex.toString()) {
//                    println("selectIndex "+selectIndex + " VatCatArr "+VatCatArr[i][0]);
                    dropDown += "<option value='" + VatCatArr[i][2] + "::" + VatCatArr[i][1] + "' selected='selected'>" + VatCatArr[i][1] + " (" + VatCatArr[i][2] + "%)" + "</option>"
//                    dropDown += "<option value='" + VatCatArr[i][2] + "' selected='selected'>" + VatCatArr[i][1] + " (" + VatCatArr[i][2] + "%)" + "</option>"
                } else {
                    dropDown += "<option value='" + VatCatArr[i][2] + "::" + VatCatArr[i][1] + "'>" + VatCatArr[i][1] + " (" + VatCatArr[i][2] + "%)" + "</option>"
                }
            }
        } else {
            dropDown += "<option>" + g.message(code: 'coreParamsHelper.noVatCategorySetup.label') + "</option>"
        }

        dropDown += "</select>"
        return dropDown
    }

    def getVatCategoryExpenseWithAjax(returnIndex, selectIndex = '0', contextCustomer = "", vendorId = 0, forReceipt = false) {

        def g = new ValidationTagLib()

        def VatCatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,categoryName,rate FROM VatCategory WHERE status=1")
        def budgetDetailsArr
        if (vendorId != 0) {
            budgetDetailsArr = new BudgetViewDatabaseService().executeQueryAtSingle("SELECT vat_category_id FROM BudgetItemExpenseDetails where budgetItemExpenseId IN(SELECT  id from budget_item_expense where vendor_id ='" + vendorId + "')group by gl_account ")
        }
        if (budgetDetailsArr) {
            selectIndex = budgetDetailsArr[0]
        }
        String dropDown = ""
        dropDown = "<select id=\"vatRate\" onchange=\"calPriceByVAT(this.value);\" name=\"vatRate\">"

        if (VatCatArr.size()) {
            for (int i = 0; i < VatCatArr.size(); i++) {
                String strVatCatId = VatCatArr[i][0].toString()
                if (strVatCatId == selectIndex.toString()) {
                    dropDown += "<option value='" + VatCatArr[i][2] + "::" + VatCatArr[i][1] + "' selected='selected'>" + VatCatArr[i][1] + " (" + VatCatArr[i][2] + "%)" + "</option>"
                } else {
                    dropDown += "<option value='" + VatCatArr[i][2] + "::" + VatCatArr[i][1] + "'>" + VatCatArr[i][1] + " (" + VatCatArr[i][2] + "%)" + "</option>"
                }
            }
        } else {
            dropDown += "<option>" + g.message(code: 'coreParamsHelper.noVatCategorySetup.label') + "</option>"
        }

        dropDown += "</select>"
        return dropDown
    }

    def getVatCategoryQuickEntryWithAjax() {

        def g = new ValidationTagLib()

        def VatCatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,categoryName,rate FROM VatCategory WHERE status=1")

        String dropDown = "<select id=\"vatOption\" onchange=\"calPriceByVAT(this.value);\" name=\"vatOption\" style=\"height: 32px; width: 60px; border: 1px solid #ffaaaa\">"

        if (VatCatArr.size()) {
            for (int i = 0; i < VatCatArr.size(); i++) {
                dropDown += "<option value='" + VatCatArr[i][0] +"::"+ VatCatArr[i][1] + "::"+ VatCatArr[i][2]+ "'>" + VatCatArr[i][1] + " (" + VatCatArr[i][2] + "%)" + "</option>"
            }
        } else {
            dropDown += "<option>" + g.message(code: 'coreParamsHelper.noVatCategorySetup.label') + "</option>"
        }

        dropDown += "</select>"
        return dropDown
    }


    def getBankAccountCodeByProduct(Long productId) {
        ///////Problem////////
        if (productId > 0) {
            def ProductArr = ProductMaster.executeQuery("FROM ProductMaster AS pro  WHERE pro.id='" + productId + "'")
            def salesAccountCodeTemp = ProductArr.get(0).salesAccountCode
            def BankAccArr = CompanyBankGlRelation.executeQuery("FROM CompanyBankGlRelation AS bank  WHERE bank.glChartCode='" + salesAccountCodeTemp + "'")

            return [bankAccountCode: BankAccArr.get(0).bankAccountCode, salesAccountCode: salesAccountCodeTemp]
        } else {
            return [bankAccountCode: '0', salesAccountCode: "0"]
        }
    }

    def getBankExpenseAccountProduct(Long productId) {
        /////////Problem/////
        if (productId > 0) {
            def ProductArr = ProductMaster.executeQuery("FROM ProductMaster AS pro  WHERE pro.id='" + productId + "'")
            def salesAccountCodeTemp = ProductArr.get(0).purchaseAccountCode
            def BankAccArr = CompanyBankGlRelation.executeQuery("FROM CompanyBankGlRelation AS bank  WHERE bank.glChartCode='" + salesAccountCodeTemp + "'")

            return [bankAccountCode: BankAccArr.get(0).bankAccountCode, purchaseAccountCode: salesAccountCodeTemp]
        } else {
            return [bankAccountCode: '0', purchaseAccountCode: "0"]
        }
    }

    def getCompanyBankAccInformation(accountId) {
        def listArr = new BudgetViewDatabaseService().executeQuery("SELECT bankAccountCode,bankAccountName FROM CompanyBankAccounts WHERE id=" + accountId)
        return listArr[0]
    }

    def getCompanyBankAccInformationByCode(accountCode) {
        def listArr = new BudgetViewDatabaseService().executeQuery("SELECT bankAccountCode,bankAccountName FROM CompanyBankAccounts WHERE bankAccountCode='" + accountCode + "'")


        return listArr[0]
    }

    def getBankAccountCodeByGl(glaccount) {
        //////Problem
        def BankAccArr = CompanyBankGlRelation.executeQuery("FROM CompanyBankGlRelation AS bank  WHERE bank.glChartCode='" + glaccount + "'")
        if (BankAccArr.size() > 0) {
            return [bankAccountCode: BankAccArr.get(0).bankAccountCode]
        } else {
            return [bankAccountCode: '']
        }
    }

    def getChartMasterInformationByCode(accountCode) {

        def listArr = new BudgetViewDatabaseService().executeQuery("SELECT accountCode,accountName FROM ChartMaster WHERE accountCode='" + accountCode + "'")
        //def listArr = ChartMaster.executeQuery("SELECT accountCode,accountName FROM ChartMaster WHERE accountCode='" + accountCode + "'")
        if (listArr.size()) {
            return listArr[0]
        } else {
            return [0: '', 1: '']
        }
    }

    def getChartMasterDropDown(returnIndex, selectIndex = '0') {

        def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,name As categoryName FROM ChartGroup where status=1")
        String dropDown = "<select name='" + returnIndex + "'>"

        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]
                def ProductArr
                ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,accountCode,accountName FROM bv.ChartMaster where status='1' AND chart_group_id='" + catId + "'")
                if (ProductArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < ProductArr.size(); j++) {

                        if (ProductArr[j][1] == selectIndex) {
                            dropDown += "<option value='" + ProductArr[j][1] + "' selected='selected'>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        } else {
                            dropDown += "<option value='" + ProductArr[j][1] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getChartMasterForReconciliationDropDown(returnIndex, selectIndex = '0') {

        def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,name As categoryName FROM ChartGroup where status=1")
        def g = new ValidationTagLib()
        String dropDown = "<select name='" + returnIndex + "'>"
        dropDown += "<option value=''>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
//Select GL Account Code

        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]
                def ProductArr
                ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,accountCode,accountName FROM bv.ChartMaster where status='1' AND chart_group_id='" + catId + "'")
                if (ProductArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < ProductArr.size(); j++) {

                        if (ProductArr[j][1] == selectIndex) {
                            dropDown += "<option value='" + ProductArr[j][1] + "' selected='selected'>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        } else {
                            dropDown += "<option value='" + ProductArr[j][1] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getInvoiceExpenseProductDropDown(returnIndex, selectIndex = '0') {

        def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,categoryName FROM ProductCategory where status=1")
        def g = new ValidationTagLib()
        // String dropDown = "<select name='" + returnIndex + "' onchange=\"goToPage(this.value)\">"
        String dropDown = "<select id=\"productId\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'/bv/invoiceExpense/selectProductRelatedInformation',success:function(data,textStatus){jQuery('#searchresults').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"productId\">"
        dropDown += "<option value=\"0\">" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
//Please Select a Product
        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]
                def ProductArr
                ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,productCode,productName,purchaseAccountCode FROM bv.ProductMaster where status='1' AND product_category_id='" + catId + "'")
                if (ProductArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < ProductArr.size(); j++) {

                        def productPurchaseAccountCode = ProductArr[j][3]
                        def CompanyBankGlRelationArr = new BudgetViewDatabaseService().executeQuery("SELECT id,bankAccountCode FROM bv.CompanyBankGlRelation where status='1' AND gl_chart_code='" + productPurchaseAccountCode + "'")
                        if (CompanyBankGlRelationArr.size()) {
                            if (ProductArr[j][0] == Integer.parseInt(selectIndex)) {
                                dropDown += "<option value='" + ProductArr[j][0] + "' selected='selected'>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                            } else {
                                dropDown += "<option value='" + ProductArr[j][0] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                            }
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getJournalChartGroupDropDown(returnIndex, selectIndex = 0) {
        //def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,name As categoryName FROM ChartGroup where status=1")
        LinkedHashMap gridResultChartGroup
        String selectChartGroup = "id,name As categoryName"
        String selectIndexesChartGroup = "id,categoryName"
        String fromChartGroup = "ChartGroup"
        String whereChartGroup = " status=1"
        String orderByChartGroup = ""
        gridResultChartGroup = new BudgetViewDatabaseService().select(selectChartGroup, fromChartGroup, whereChartGroup, orderByChartGroup, '', 'true', selectIndexesChartGroup)
        def CatArr = gridResultChartGroup['dataGridList']

        //String dropDown = "<select id=\"JournalChartId\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'/bv/JournalEntry/selectChartRelatedInformation',success:function(data,textStatus){jQuery('#searchresults').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"JournalChartId\">"
        String dropDown = "<select name='" + returnIndex + "'>"
        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]

                //def ProductArr
                //ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,accountCode,accountName FROM bv.ChartMaster where status='1' AND chart_group_id='" + catId + "'")
                LinkedHashMap gridResultProductArr
                String selectProductArr = "id,accountCode,accountName"
                String selectIndexesProductArr = "id,accountCode,accountName"
                String fromProductArr = "ChartMaster"
                String whereProductArr = "status='1' AND chart_group_id='" + catId + "'"
                String orderByProductArr = ""
                gridResultProductArr = new BudgetViewDatabaseService().select(selectProductArr, fromProductArr, whereProductArr, orderByProductArr, '', 'true', selectIndexesProductArr)
                def ProductArr = gridResultProductArr['dataGridList']

                if (ProductArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < ProductArr.size(); j++) {

                        if (ProductArr[j][1] == selectIndex) {
                            dropDown += "<option value='" + ProductArr[j][1] + "' selected='selected'>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        } else {
                            dropDown += "<option value='" + ProductArr[j][1] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getSubJournalChartGroupDropDown(returnIndex, selectIndex = 0, bankAccountCategory) {

        def CatArr = ""
        def catId = ""
        def glCode = ""
        LinkedHashMap gridResultProductArr
        String selectProductArr = ""
        String selectIndexesProductArr = ""
        String fromProductArr = ""
        String whereProductArr = ""
        String orderByProductArr = ""
        def productArr
        if (bankAccountCategory == "cba" || bankAccountCategory == null) {
            CatArr = new BudgetViewDatabaseService().executeQuery("SELECT chartGroupForCash FROM DebitCreditGlSetup where id=1")
            catId = CatArr[0][0]
        } else if (bankAccountCategory == "pba") {
            CatArr = new BudgetViewDatabaseService().executeQuery("SELECT privateWithdrawalCode FROM DebitCreditGlSetup where id=1")
            glCode = CatArr[0][0]
        }

        String dropDown = "<select name='" + returnIndex + "' tabindex=\"6\">"
        if (CatArr.size()) {
            //ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,accountCode,accountName FROM bv.ChartMaster where status='1' AND chart_group_id='" + catId + "'")
            if (bankAccountCategory == "cba" || bankAccountCategory == null) {

                selectProductArr = "id,accountCode,accountName"
                selectIndexesProductArr = "id,accountCode,accountName"
                fromProductArr = "ChartMaster"
                whereProductArr = "status='1' AND chart_group_id='" + catId + "'"
                orderByProductArr = ""

            } else if (bankAccountCategory == "pba") {

                selectProductArr = "id,accountCode,accountName"
                selectIndexesProductArr = "id,accountCode,accountName"
                fromProductArr = "ChartMaster"
                whereProductArr = "status='1' AND  account_code='" + glCode + "' "
                orderByProductArr = ""

            }
            gridResultProductArr = new BudgetViewDatabaseService().select(selectProductArr, fromProductArr, whereProductArr, orderByProductArr, '', 'true', selectIndexesProductArr)
            productArr = gridResultProductArr['dataGridList']

            if (productArr.size()) {
                for (int j = 0; j < productArr.size(); j++) {
                    if (productArr[j][1] == selectIndex) {
                        dropDown += "<option value='" + productArr[j][1] + "' selected='selected'>" + productArr[j][1] + "  " + productArr[j][2] + "</option>"
                    } else {
                        dropDown += "<option value='" + productArr[j][1] + "' >" + productArr[j][1] + "  " + productArr[j][2] + "</option>"
                    }
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getBudgetDetailsChartGroupDropDown(returnIndex, selectIndex = 0, budgetItemId = 0, vendorId = 0, glAccount = 0) {
        def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,name As categoryName FROM ChartGroup where status=1")
        def budgetDetailsArr
        if (budgetItemId != 0) {
            budgetDetailsArr = new BudgetViewDatabaseService().executeQueryAtSingle("SELECT glAccount FROM BudgetItemExpenseDetails where budgetItemExpenseId IN(SELECT  id from budget_item_expense where vendor_id ='" + vendorId + "')group by gl_account ")
        } else {
            budgetDetailsArr = new BudgetViewDatabaseService().executeQueryAtSingle("SELECT glAccount FROM BudgetItemExpenseDetails where budgetItemExpenseId IN(SELECT  id from budget_item_expense where vendor_id ='" + vendorId + "')group by gl_account ")
        }
        if ((budgetItemId == 0 || budgetItemId == "null") && vendorId == 0) {
            budgetDetailsArr = new BudgetViewDatabaseService().executeQueryAtSingle("SELECT glAccount FROM BudgetItemExpenseDetails where glAccount=${glAccount} group by glAccount ORDER BY id LIMIT 1")
        }
        String tempGLsJoin = ""
        ArrayList tmpGLs = new ArrayList()

        if (budgetDetailsArr.size()) {
            selectIndex = budgetDetailsArr[0]
            for (int i = 0; i < budgetDetailsArr.size(); i++) {
                tmpGLs << budgetDetailsArr[i]
            }
            tempGLsJoin = tmpGLs.join(",")
        }

        String dropDown = "<select id='" + returnIndex + "' name='" + returnIndex + "'>"
        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]
                //def productArr
                ArrayList productArr = new ArrayList()
                if (tempGLsJoin) {
                    productArr = new BudgetViewDatabaseService().executeQuery("SELECT id,accountCode,accountName FROM bv.ChartMaster where status='1' AND chart_group_id='" + catId + "' AND accountCode IN(" + tempGLsJoin + ")")
                } else {
                    //productArr = ChartMaster.executeQuery("SELECT id,accountCode,accountName FROM bv.ChartMaster where status='1' AND chart_group_id='" + catId + "'")
                }
                if (productArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < productArr.size(); j++) {
                        def glAccountCode = productArr[j][1]
                        if (glAccountCode == selectIndex.toString()) {
                            dropDown += "<option value='" + productArr[j][1] + "' selected='selected'>" + productArr[j][1] + "  " + productArr[j][2] + "</option>"
                        } else {
                            dropDown += "<option value='" + productArr[j][1] + "' >" + productArr[j][1] + "  " + productArr[j][2] + "</option>"
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    /****
     *     Select Gl Account for the Invoice income parr
     *
     *** */

    def getBudgetDetailsChartGroupDropDownForIncomeIvoice(returnIndex, selectIndex = 0, budgetItemId = 0, customerId = 0, glAccount = 0) {
        def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,name As categoryName FROM ChartGroup where status=1")
        def budgetDetailsArr

        if (budgetItemId != 0) {
            budgetDetailsArr = new BudgetViewDatabaseService().executeQueryAtSingle("SELECT glAccount FROM BudgetItemIncomeDetails where budgetItemIncomeId IN(SELECT  id from budget_item_income where customer_id ='" + customerId + "')group by gl_account")
        } else {
            budgetDetailsArr = new BudgetViewDatabaseService().executeQueryAtSingle("SELECT glAccount FROM BudgetItemIncomeDetails where budgetItemIncomeId IN(SELECT  id from budget_item_income where customer_id ='" + customerId + "')group by gl_account")
        }

        if ((budgetItemId == 0 || budgetItemId == "null") && customerId == 0) {
            budgetDetailsArr = new BudgetViewDatabaseService().executeQueryAtSingle("SELECT glAccount FROM BudgetItemIncomeDetails where glAccount=" + glAccount)
        }

        String tempGLsJoin = ""
        ArrayList tmpGLs = new ArrayList()
        if (budgetDetailsArr.size()) {
            for (int i = 0; i < budgetDetailsArr.size(); i++) {
                tmpGLs << budgetDetailsArr[i]
            }
            tempGLsJoin = tmpGLs.join(",")
        }

        String dropDown = "<select id='journalId' name='" + returnIndex + "'>"
        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]
                //def productArr
                ArrayList productArr = new ArrayList()
                if (tempGLsJoin) {
                    productArr = new BudgetViewDatabaseService().executeQuery("SELECT id,accountCode,accountName FROM bv.ChartMaster where status='1' AND chart_group_id='" + catId + "' AND accountCode IN(" + tempGLsJoin + ")")
                } else {
                    //productArr = ChartMaster.executeQuery("SELECT id,accountCode,accountName FROM bv.ChartMaster where status='1' AND chart_group_id='" + catId + "'")
                }
                if (productArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < productArr.size(); j++) {

                        if (productArr[j][1] == selectIndex) {
                            dropDown += "<option value='" + productArr[j][1] + "' selected='selected'>" + productArr[j][1] + "  " + productArr[j][2] + "</option>"
                        } else {
                            dropDown += "<option value='" + productArr[j][1] + "' >" + productArr[j][1] + "  " + productArr[j][2] + "</option>"
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }
    /***************** END ****************/
    def getBudgetChartGroupDropDown(returnIndex, selectIndex = 0) {
        def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,name As categoryName FROM ChartGroup where status=1")
        String dropDown = "<select id='" + returnIndex + "' name='" + returnIndex + "'>"
        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]
                def ProductArr
                ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,accountCode,accountName FROM bv.ChartMaster where status='1' AND chart_group_id='" + catId + "'")
                if (ProductArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < ProductArr.size(); j++) {

                        if (ProductArr[j][1] == selectIndex) {
                            dropDown += "<option value='" + ProductArr[j][1] + "' selected='selected'>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        } else {
                            dropDown += "<option value='" + ProductArr[j][1] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getGlAccountCodeStartDropDown(returnIndex, selectIndex = 0) {
        LinkedHashMap gridResultChartGroup
        String selectChartGroup = "id,name As categoryName"
        String selectIndexesChartGroup = "id,categoryName"
        String fromChartGroup = "ChartGroup"
        String whereChartGroup = " status=1"
        String orderByChartGroup = ""
        gridResultChartGroup = new BudgetViewDatabaseService().select(selectChartGroup, fromChartGroup, whereChartGroup, orderByChartGroup, '', 'true', selectIndexesChartGroup)
        def CatArr = gridResultChartGroup['dataGridList']

        String dropDown = "<select id='glAccountStartDropDown' name='glAccountStartDropDown'>"
        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]

                LinkedHashMap gridResultProductArr
                String selectProductArr = "id,accountCode,accountName"
                String selectIndexesProductArr = "id,accountCode,accountName"
                String fromProductArr = "ChartMaster"
                String whereProductArr = "status='1' AND chart_group_id='" + catId + "'"
                String orderByProductArr = "accountName"
                gridResultProductArr = new BudgetViewDatabaseService().select(selectProductArr, fromProductArr, whereProductArr, orderByProductArr, '', 'true', selectIndexesProductArr)
                def ProductArr = gridResultProductArr['dataGridList']

                if (ProductArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < ProductArr.size(); j++) {

                        if (ProductArr[j][1] == selectIndex) {
                            dropDown += "<option value='" + ProductArr[j][1] + "' selected='selected'>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        } else {
                            dropDown += "<option value='" + ProductArr[j][1] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getGlAccountCodeEndDropDown(returnIndex, selectIndex = 0) {
        LinkedHashMap gridResultChartGroup
        String selectChartGroup = "id,name As categoryName"
        String selectIndexesChartGroup = "id,categoryName"
        String fromChartGroup = "ChartGroup"
        String whereChartGroup = " status=1"
        String orderByChartGroup = ""
        gridResultChartGroup = new BudgetViewDatabaseService().select(selectChartGroup, fromChartGroup, whereChartGroup, orderByChartGroup, '', 'true', selectIndexesChartGroup)
        def CatArr = gridResultChartGroup['dataGridList']

        String dropDown = "<select id='glAccountEndDropDown' name='glAccountEndDropDown'>"
        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]

                LinkedHashMap gridResultProductArr
                String selectProductArr = "id,accountCode,accountName"
                String selectIndexesProductArr = "id,accountCode,accountName"
                String fromProductArr = "ChartMaster"
                String whereProductArr = "status='1' AND chart_group_id='" + catId + "'"
                String orderByProductArr = "accountName"
                gridResultProductArr = new BudgetViewDatabaseService().select(selectProductArr, fromProductArr, whereProductArr, orderByProductArr, '', 'true', selectIndexesProductArr)
                def ProductArr = gridResultProductArr['dataGridList']

                if (ProductArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < ProductArr.size(); j++) {

                        if (ProductArr[j][1] == selectIndex) {
                            dropDown += "<option value='" + ProductArr[j][1] + "' selected='selected'>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        } else {
                            dropDown += "<option value='" + ProductArr[j][1] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }


//    def CustomerTypeDropDown(returnIndex, selectionVal, isNull) {
//
//        ArrayList mapStatuseArr = new ArrayList()
//        def g=new ValidationTagLib()
//        mapStatuseArr = [
////               ['0', 'Select Status'],
//                 ['bn', g.message(code: 'customerMaster.changingCustomer.dropdown')],
//                 ['cn', g.message(code: 'customerMaster.fixedCustomer.dropdown')],]
//
//        String dropDown = "<select name='" + returnIndex + "'tabindex=\"7\">"
//
//        for (int i = 0; i < mapStatuseArr.size(); i++) {
//            if (mapStatuseArr[i][0].toString() == selectionVal) {
//                dropDown += "<option value='" + mapStatuseArr[i][0] + "' selected='selected'>" + mapStatuseArr[i][1] +  "</option>"
//            } else {
//                dropDown += "<option value='" + mapStatuseArr[i][0] + "'>" + mapStatuseArr[i][1] + "</option>"
//            }
//        }
//
//        dropDown += "</select>"
//        return dropDown
//
//    }

    def getReportAllFileType(selectedValue) {

        ArrayList mapStatuseArr = new ArrayList()
        mapStatuseArr = [['pdf', "PDF"],['xlsx', "Excel"],['HTML', "HTML"],
                         ['csv', "CSV"]]
        String dropDown = "<select id='reportFileType' name='reportFileType'>"
        for (int i = 0; i < mapStatuseArr.size(); i++) {
            if (mapStatuseArr[i][0].toString() == selectedValue) {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "' selected='selected'>" + mapStatuseArr[i][1] + "</option>"
            } else {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "'>" + mapStatuseArr[i][1] + "</option>"
            }
        }
        dropDown += "</select>"
        return dropDown

    }

    def getReportFileType(selectedValue) {

        ArrayList mapStatuseArr = new ArrayList()
        mapStatuseArr = [['pdf', "PDF"],
                         ['xlsx', "XlSX"]]
        String dropDown = "<select id='reportFileType' name='reportFileType'>"
        for (int i = 0; i < mapStatuseArr.size(); i++) {
            if (mapStatuseArr[i][0].toString() == selectedValue) {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "' selected='selected'>" + mapStatuseArr[i][1] + "</option>"
            } else {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "'>" + mapStatuseArr[i][1] + "</option>"
            }
        }
        dropDown += "</select>"
        return dropDown

    }

    def getDefaultGlSetupLiabilities(returnIndex, selectIndex) {

        def CC = new BudgetViewDatabaseService().executeQuery("SELECT id FROM bv.ChartClass WHERE chartClassTypeId = 2")

        def con = ''
        for (int p = 0; p < CC.size(); p++) {
            if (p == 0) {
                con += ' chartClassId=' + CC[p][0]
            } else {
                con += ' OR chartClassId=' + CC[p][0]
            }
        }

        def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,name As categoryName FROM ChartGroup where status=1 AND " + con)
        String dropDown = "<select name='" + returnIndex + "'>"

        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]
                def ProductArr
                ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,accountCode,accountName FROM bv.ChartMaster where status='1' AND chart_group_id='" + catId + "'")
                if (ProductArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < ProductArr.size(); j++) {

                        if (ProductArr[j][1] == selectIndex) {
                            dropDown += "<option value='" + ProductArr[j][1] + "' selected='selected'>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        } else {
                            dropDown += "<option value='" + ProductArr[j][1] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }

        dropDown += "</select>"
        return dropDown
    }

    def getDefaultGlSetupAssetsGroup(returnIndex, selectIndex) {

        def CC = new BudgetViewDatabaseService().executeQuery("SELECT id FROM bv.ChartClass WHERE chartClassTypeId = 1")

        def con = ''
        for (int p = 0; p < CC.size(); p++) {
            if (p == 0) {
                con += ' chartClassId=' + CC[p][0]
            } else {
                con += ' OR chartClassId=' + CC[p][0]
            }
        }

        def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,name As categoryName FROM ChartGroup where status=1 AND " + con)
        String dropDown = "<select name='" + returnIndex + "'>"

        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {

                if (CatArr[i][0] == selectIndex) {
                    dropDown += "<option value='" + CatArr[i][0] + "' selected='selected'>" + CatArr[i][1] + "</option>"
                } else {
                    dropDown += "<option value='" + CatArr[i][0] + "' >" + CatArr[i][1] + "</option>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }


    def getBudgetChartGroupDropDownIncome(returnIndex, selectIndex = "") {

        if (selectIndex instanceof java.lang.String) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

        LinkedHashMap gridResult
        String select = "id"
        String selectIndexes = "id"
        String from = "ChartClass"
        String where = "chart_class_type_id IN(4) AND status=1"
        String orderBy = ""
        gridResult = new BudgetViewDatabaseService().select(select, from, where, orderBy, '', 'true', selectIndexes)
        def ChartClassArr = []
        gridResult['dataGridList'].each { phn ->
            ChartClassArr.add(phn[0])
        }
        //def ChartClassArr = new BudgetViewDatabaseService().executeQuery("SELECT id FROM ChartClass WHERE chartClassType IN(4) AND status=1")
        def ChartClassString = ChartClassArr.join(",")

        LinkedHashMap gridResultChartGroup
        String selectChartGroup = "id,name As categoryName"
        String selectIndexesChartGroup = "id,categoryName"
        String fromChartGroup = "ChartGroup"
        String whereChartGroup = "chartClassId IN(" + ChartClassString + ") AND status=1"
        String orderByChartGroup = ""
        gridResultChartGroup = new BudgetViewDatabaseService().select(selectChartGroup, fromChartGroup, whereChartGroup, orderByChartGroup, '', 'true', selectIndexesChartGroup)

        def CatArr = gridResultChartGroup['dataGridList']
        //def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,name As categoryName FROM ChartGroup where chartClass IN(" + ChartClassString + ") AND status=1")

        String dropDown = "<select id='" + returnIndex + "' name='" + returnIndex + "'>"
        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]

                LinkedHashMap gridResultProductArr
                String selectProductArr = "id,accountCode,accountName"
                String selectIndexesProductArr = "id,accountCode,accountName"
                String fromProductArr = "ChartMaster"
                String whereProductArr = "status='1' AND chart_group_id='" + catId + "'"
                String orderByProductArr = ""
                gridResultProductArr = new BudgetViewDatabaseService().select(selectProductArr, fromProductArr, whereProductArr, orderByProductArr, '', 'true', selectIndexesProductArr)

                def ProductArr = gridResultProductArr['dataGridList']
                // def ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,accountCode,accountName FROM bv.ChartMaster where status='1' AND chart_group_id='" + catId + "'")

                if (ProductArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < ProductArr.size(); j++) {

                        String strTypeIndex = ProductArr[j][1] + "";
                        if (strTypeIndex == selectIndex) {
                            dropDown += "<option value='" + ProductArr[j][1] + "' selected='selected'>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        } else {
                            dropDown += "<option value='" + ProductArr[j][1] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }


    def getBudgetChartGroupDropDownExpanse(returnIndex, selectIndex = 0) {

        LinkedHashMap gridResult
        String select = "id"
        String selectIndexes = "id"
        String from = "ChartClass"
        String where = "chart_class_type_id IN(5,6,7) AND status=1"
        String orderBy = ""
        gridResult = new BudgetViewDatabaseService().select(select, from, where, orderBy, '', 'true', selectIndexes)
        def ChartClassArr = []
        gridResult['dataGridList'].each { phn ->
            ChartClassArr.add(phn[0])
        }
        //def ChartClassArr = new BudgetViewDatabaseService().executeQueryAtSingle("SELECT id FROM ChartClass WHERE chartClassTypeId IN(5,6,7) AND status=1")

        def ChartClassString = ChartClassArr.join(",")

        LinkedHashMap gridResultChartGroup
        String selectChartGroup = "id,name As categoryName"
        String selectIndexesChartGroup = "id,categoryName"
        String fromChartGroup = "ChartGroup"
        String whereChartGroup = "chartClassId IN(" + ChartClassString + ") AND status=1"
        String orderByChartGroup = ""
        gridResultChartGroup = new BudgetViewDatabaseService().select(selectChartGroup, fromChartGroup, whereChartGroup, orderByChartGroup, '', 'true', selectIndexesChartGroup)

        def CatArr = gridResultChartGroup['dataGridList']
        //def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,name As categoryName FROM ChartGroup where chartClassId IN(" + ChartClassString + ") AND status=1")

        String dropDown = "<select id='" + returnIndex + "' name='" + returnIndex + "' tabindex=\"10\">"
        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]

                LinkedHashMap gridResultProductArr
                String selectProductArr = "id,accountCode,accountName"
                String selectIndexesProductArr = "id,accountCode,accountName"
                String fromProductArr = "ChartMaster"
                String whereProductArr = "status='1' AND chart_group_id='" + catId + "'"
                String orderByProductArr = ""
                gridResultProductArr = new BudgetViewDatabaseService().select(selectProductArr, fromProductArr, whereProductArr, orderByProductArr, '', 'true', selectIndexesProductArr)

                def ProductArr = gridResultProductArr['dataGridList']
                //def ProductArr
                //ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,accountCode,accountName FROM bv.ChartMaster where status='1' AND chart_group_id='" + catId + "'")
                if (ProductArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < ProductArr.size(); j++) {

                        if (ProductArr[j][1] == selectIndex) {
                            dropDown += "<option value='" + ProductArr[j][1] + "' selected='selected'>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        } else {
                            dropDown += "<option value='" + ProductArr[j][1] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }


    def getBalanceSheetGlCode(returnIndex, selectIndex = 0) {

        LinkedHashMap gridResult
        String select = "id"
        String selectIndexes = "id"
        String from = "ChartClass"
        String where = "chart_class_type_id IN(1,2,3) AND status=1"
        String orderBy = ""
        gridResult = new BudgetViewDatabaseService().select(select, from, where, orderBy, '', 'true', selectIndexes)
        def ChartClassArr = []
        gridResult['dataGridList'].each { phn ->
            ChartClassArr.add(phn[0])
        }
        //def ChartClassArr = new BudgetViewDatabaseService().executeQueryAtSingle("SELECT id FROM ChartClass WHERE chartClassTypeId IN(5,6,7) AND status=1")

        def ChartClassString = ChartClassArr.join(",")

        LinkedHashMap gridResultChartGroup
        String selectChartGroup = "id,name As categoryName"
        String selectIndexesChartGroup = "id,categoryName"
        String fromChartGroup = "ChartGroup"
        String whereChartGroup = "chartClassId IN(" + ChartClassString + ") AND status=1"
        String orderByChartGroup = ""
        gridResultChartGroup = new BudgetViewDatabaseService().select(selectChartGroup, fromChartGroup, whereChartGroup, orderByChartGroup, '', 'true', selectIndexesChartGroup)

        def CatArr = gridResultChartGroup['dataGridList']
        //def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,name As categoryName FROM ChartGroup where chartClassId IN(" + ChartClassString + ") AND status=1")

        String dropDown = "<select id='" + returnIndex + "' name='" + returnIndex + "' tabindex=\"10\">"
        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]

                LinkedHashMap gridResultProductArr
                String selectProductArr = "id,accountCode,accountName"
                String selectIndexesProductArr = "id,accountCode,accountName"
                String fromProductArr = "ChartMaster"
                String whereProductArr = "status='1' AND chart_group_id='" + catId + "'"
                String orderByProductArr = ""
                gridResultProductArr = new BudgetViewDatabaseService().select(selectProductArr, fromProductArr, whereProductArr, orderByProductArr, '', 'true', selectIndexesProductArr)

                def ProductArr = gridResultProductArr['dataGridList']
                //def ProductArr
                //ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,accountCode,accountName FROM bv.ChartMaster where status='1' AND chart_group_id='" + catId + "'")
                if (ProductArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < ProductArr.size(); j++) {

                        if (ProductArr[j][1].toString() == selectIndex.toString()) {
                            dropDown += "<option value='" + ProductArr[j][1] + "' selected='selected'>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        } else {
                            dropDown += "<option value='" + ProductArr[j][1] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getChartGroupDropDown(returnIndex, selectIndex = 0) {

        def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,name As categoryName FROM ChartClass where status=1")
        String dropDown = "<select name='" + returnIndex + "'>"
        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]
                def ChartGroupArr
                ChartGroupArr = new BudgetViewDatabaseService().executeQuery("SELECT id,name FROM bv.ChartGroup where status='1' AND chart_class_id='" + catId + "'")
                if (ChartGroupArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < ChartGroupArr.size(); j++) {

                        if (ChartGroupArr[j][0] == selectIndex) {
                            dropDown += "<option value='" + ChartGroupArr[j][0] + "' selected='selected'>" + ChartGroupArr[j][1] + "</option>"
                        } else {
                            dropDown += "<option value='" + ChartGroupArr[j][0] + "' >" + ChartGroupArr[j][1] + "</option>"
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getCompanyBankAccountDropDown(returnIndex, selectIndex = '') {

        def CatArr = new BudgetViewDatabaseService().executeQueryAtSingle("SELECT bankAccountType FROM CompanyBankAccounts where status=1 GROUP BY bankAccountType")
        println('CatArr' + CatArr)
        String dropDown = "<select name='" + returnIndex + "'>"
        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i]
                println('catId==' + catId)
                def CompanyBankAccArr
                CompanyBankAccArr = new BudgetViewDatabaseService().executeQuery("SELECT id,bankAccountCode,bankAccountName FROM bv.CompanyBankAccounts where status='1' AND bank_account_type='" + catId + "'")

                println('CompanyBankAccArr==' + CompanyBankAccArr)

                if (CompanyBankAccArr.size()) {
                    dropDown += "<optgroup label='" + catId + "'>"
                    for (int j = 0; j < CompanyBankAccArr.size(); j++) {

                        if (CompanyBankAccArr[j][1] == selectIndex) {
                            dropDown += "<option value='" + CompanyBankAccArr[j][1] + "' selected='selected'>" + CompanyBankAccArr[j][1] + " - " + CompanyBankAccArr[j][2] + "</option>"
                        } else {
                            dropDown += "<option value='" + CompanyBankAccArr[j][1] + "' >" + CompanyBankAccArr[j][1] + " - " + CompanyBankAccArr[j][2] + "</option>"
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getProductGLInformation(productId) {
        def listArr = new BudgetViewDatabaseService().executeQuery("SELECT adjustmentAccountCode,inventoryAccountCode,purchaseAccountCode,salesAccountCode FROM ProductMaster WHERE id=" + productId)
        return listArr[0]
    }

    /**
     * GENERATE INTERNAL BANKING Invoice NUmber
     * */
    def getInternalBankingCode() {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=10")
        def Prefix = PrefixDataArr[0][0]
        def PrefixLength = PrefixDataArr[0][1]
        ///////////////////MAX CUSTOMER ID/////
        def InternalBankingArr = new BudgetViewDatabaseService().executeQuery("SELECT MAX(id) FROM bv.InternalBanking")

        def newSequence
        if (InternalBankingArr[0]) {
            newSequence = InternalBankingArr[0] + 1
        } else {
            newSequence = 1
        }

        def InternalBankingCode
        if (PrefixLength == 2) {
            InternalBankingCode = String.format("%02d", newSequence)
        } else if (PrefixLength == 3) {
            InternalBankingCode = String.format("%03d", newSequence)
        } else if (PrefixLength == 4) {
            InternalBankingCode = String.format("%04d", newSequence)
        } else if (PrefixLength == 5) {
            InternalBankingCode = String.format("%05d", newSequence)
        } else if (PrefixLength == 6) {
            InternalBankingCode = String.format("%06d", newSequence)
        } else if (PrefixLength == 7) {
            InternalBankingCode = String.format("%07d", newSequence)
        } else if (PrefixLength == 8) {
            InternalBankingCode = String.format("%08d", newSequence)
        } else if (PrefixLength == 9) {
            InternalBankingCode = String.format("%09d", newSequence)
        }

        return InternalBankingCode
    }

    /**
     *  Show internal Banking Invoice Code
     * */
    def showInternalBankingCode() {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=10")
        def Prefix = PrefixDataArr[0][0]
        return Prefix
    }

    def getGeneratedCustomerCode() {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=1")
        def Prefix = PrefixDataArr[0][0]
        def PrefixLength = PrefixDataArr[0][1]
        ///////////////////MAX CUSTOMER ID/////
        def CustomerDataArr = new BudgetViewDatabaseService().executeQuery("SELECT MAX(id) FROM bv.CustomerMaster")

        def newSequence
        if (CustomerDataArr[0]) {
            newSequence = CustomerDataArr[0] + 1
        } else {
            newSequence = 1
        }

        def CustomerCode
        if (PrefixLength == 2) {
            CustomerCode = String.format("%02d", newSequence)
        } else if (PrefixLength == 3) {
            CustomerCode = String.format("%03d", newSequence)
        } else if (PrefixLength == 4) {
            CustomerCode = String.format("%04d", newSequence)
        } else if (PrefixLength == 5) {
            CustomerCode = String.format("%05d", newSequence)
        } else if (PrefixLength == 6) {
            CustomerCode = String.format("%06d", newSequence)
        } else if (PrefixLength == 7) {
            CustomerCode = String.format("%07d", newSequence)
        } else if (PrefixLength == 8) {
            CustomerCode = String.format("%08d", newSequence)
        } else if (PrefixLength == 9) {
            CustomerCode = String.format("%09d", newSequence)
        }

        return CustomerCode
    }

    def showGeneratedCustomerCode() {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=1")
        def Prefix = PrefixDataArr[0][0]
        return Prefix
    }


    def showGeneratedPrivateCode() {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=13")
        def Prefix = PrefixDataArr[0][0]
        return Prefix
    }

    def showGeneratedReservationCode() {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=14")
        def Prefix = PrefixDataArr[0][0]
        return Prefix
    }

    def getGeneratedVendorCode() {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=2")
        def Prefix = PrefixDataArr[0][0]
        def PrefixLength = PrefixDataArr[0][1]
        def VendorCode = getCodeSequence(Prefix, PrefixLength)

        return VendorCode
    }

    def getCodeSequence(prefix, prefixLength) {

        def maxIdArr
        def formatString
        ////////MAX VENDOR ID////////
        if (prefix == "CUS") {
            maxIdArr = new BudgetViewDatabaseService().executeQuery("SELECT MAX(id) FROM bv.CustomerMaster")
        } else if (prefix == "VEN") {
            maxIdArr = new BudgetViewDatabaseService().executeQuery("SELECT MAX(id) FROM bv.VendorMaster")
        } else if (prefix == "PRO") {
            maxIdArr = new BudgetViewDatabaseService().executeQuery("SELECT MAX(id) FROM bv.VendorMaster")
        } else if (prefix == "J") {
            maxIdArr = new BudgetViewDatabaseService().executeQuery("SELECT MAX(id) FROM bv.JournalEntry")
        } else if (prefix == "Q") {
            maxIdArr = new BudgetViewDatabaseService().executeQuery("SELECT MAX(id) FROM bv.QuickEntry")
        } else if (prefix == "EXP") {
            maxIdArr = new BudgetViewDatabaseService().executeQuery("SELECT MAX(id) FROM bv.BudgetItemExpense")
        } else if (prefix == "INC") {
            maxIdArr = new BudgetViewDatabaseService().executeQuery("SELECT MAX(id) FROM bv.BudgetItemIncome")
        } else if (prefix == "INVE") {
            maxIdArr = new BudgetViewDatabaseService().executeQuery("SELECT MAX(id) FROM bv.InvoiceExpense")
        } else if (prefix == "INVI") {
            maxIdArr = new BudgetViewDatabaseService().executeQuery("SELECT MAX(id) FROM bv.InvoiceIncome")
        } else if (prefix == "RE") {
            maxIdArr = new BudgetViewDatabaseService().executeQuery("SELECT MAX(id) FROM bv.InvoiceExpense")
        } else if (prefix == "PVT") {
            maxIdArr = new BudgetViewDatabaseService().executeQuery("SELECT MAX(id) FROM bv.privateBudgetMaster")
        } else if (prefix == "RESV") {
            maxIdArr = new BudgetViewDatabaseService().executeQuery("SELECT MAX(id) FROM bv.reservationBudgetMaster")
        } else if (prefix == "PVT_ITEM") {
            maxIdArr = new BudgetViewDatabaseService().executeQuery("SELECT MAX(id) FROM bv.privateBudgetItem")
        } else if (prefix == "RESEV_ITEM") {
            maxIdArr = new BudgetViewDatabaseService().executeQuery("SELECT MAX(id) FROM bv.reservationBudgetItem")
        }

        def newSequence
        if (maxIdArr[0][0]) {
            newSequence = maxIdArr[0][0] + 1
        } else {
            newSequence = 1
        }

        def sequenceCode = ""
        if (prefixLength == 2) {
            sequenceCode = String.format("%02d", newSequence)
        } else if (prefixLength == 3) {
            sequenceCode = String.format("%03d", newSequence)
        } else if (prefixLength == 4) {
            sequenceCode = String.format("%04d", newSequence)
        } else if (prefixLength == 5) {
            sequenceCode = String.format("%05d", newSequence)
        } else if (prefixLength == 6) {
            sequenceCode = String.format("%06d", newSequence)
        } else if (prefixLength == 7) {
            sequenceCode = String.format("%07d", newSequence)
        } else if (prefixLength == 8) {
            sequenceCode = String.format("%08d", newSequence)
        } else if (prefixLength == 9) {
            sequenceCode = String.format("%09d", newSequence)
        }

        return sequenceCode;
    }

    def showGeneratedVendorCode() {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=2")
        def Prefix = PrefixDataArr[0][0]
        return Prefix
    }

    def showGeneratedBudgetExpanseCode() {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=6")
        def Prefix = PrefixDataArr[0][0]
        return Prefix
    }

    def getGeneratedInvoiceExpanse() {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=7")
        def Prefix = PrefixDataArr[0][0]
        def PrefixLength = PrefixDataArr[0][1]
        ///////////////////MAX CUSTOMER ID/////
        def InvoiceIncomeDataArr = new BudgetViewDatabaseService().executeQuery("SELECT MAX(invoiceNo) FROM bv.InvoiceExpense WHERE isBookReceive=0")

        def newSequence
        if (InvoiceIncomeDataArr[0]) {
            newSequence = Integer.parseInt(InvoiceIncomeDataArr[0]) + 1
        } else {
            newSequence = 1
        }

        def InvoiceNo
        if (PrefixLength == 2) {
            InvoiceNo = String.format("%02d", newSequence)
        } else if (PrefixLength == 3) {
            InvoiceNo = String.format("%03d", newSequence)
        } else if (PrefixLength == 4) {
            InvoiceNo = String.format("%04d", newSequence)
        } else if (PrefixLength == 5) {
            InvoiceNo = String.format("%05d", newSequence)
        } else if (PrefixLength == 6) {
            InvoiceNo = String.format("%06d", newSequence)
        } else if (PrefixLength == 7) {
            InvoiceNo = String.format("%07d", newSequence)
        } else if (PrefixLength == 8) {
            InvoiceNo = String.format("%08d", newSequence)
        } else if (PrefixLength == 9) {
            InvoiceNo = String.format("%09d", newSequence)
        }

        return InvoiceNo
    }

    def getGeneratedReceipt() {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=12")
        def Prefix = PrefixDataArr[0][0]
        def PrefixLength = PrefixDataArr[0][1]
        ///////////////////MAX CUSTOMER ID/////
        def InvoiceIncomeDataArr = new BudgetViewDatabaseService().executeQuery("SELECT MAX(invoiceNo) FROM bv.InvoiceExpense WHERE isBookReceive=1 ORDER BY id DESC")


        def newSequence
        if (InvoiceIncomeDataArr[0]) {
            newSequence = Integer.parseInt(InvoiceIncomeDataArr[0]) + 1
        } else {
            newSequence = 1
        }

        def InvoiceNo
        if (PrefixLength == 2) {
            InvoiceNo = String.format("%02d", newSequence)
        } else if (PrefixLength == 3) {
            InvoiceNo = String.format("%03d", newSequence)
        } else if (PrefixLength == 4) {
            InvoiceNo = String.format("%04d", newSequence)
        } else if (PrefixLength == 5) {
            InvoiceNo = String.format("%05d", newSequence)
        } else if (PrefixLength == 6) {
            InvoiceNo = String.format("%06d", newSequence)
        } else if (PrefixLength == 7) {
            InvoiceNo = String.format("%07d", newSequence)
        } else if (PrefixLength == 8) {
            InvoiceNo = String.format("%08d", newSequence)
        } else if (PrefixLength == 9) {
            InvoiceNo = String.format("%09d", newSequence)
        }

        return InvoiceNo
    }

    def showGeneratedInvoiceExpanse() {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=7")
        def Prefix = PrefixDataArr[0][0]
        return Prefix
    }

    def getGeneratedInvoiceIncome() {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=8")
        def Prefix = PrefixDataArr[0][0]
        def PrefixLength = PrefixDataArr[0][1]
        ///////////////////MAX CUSTOMER ID/////
        def InvoiceIncomeDataArr = new BudgetViewDatabaseService().executeQuery("SELECT MAX(id) FROM bv.InvoiceIncome")

        def newSequence
        if (InvoiceIncomeDataArr[0]) {
            newSequence = InvoiceIncomeDataArr[0] + 1
        } else {
            newSequence = 1
        }

        def InvoiceNo
        if (PrefixLength == 2) {
            InvoiceNo = String.format("%02d", newSequence)
        } else if (PrefixLength == 3) {
            InvoiceNo = String.format("%03d", newSequence)
        } else if (PrefixLength == 4) {
            InvoiceNo = String.format("%04d", newSequence)
        } else if (PrefixLength == 5) {
            InvoiceNo = String.format("%05d", newSequence)
        } else if (PrefixLength == 6) {
            InvoiceNo = String.format("%06d", newSequence)
        } else if (PrefixLength == 7) {
            InvoiceNo = String.format("%07d", newSequence)
        } else if (PrefixLength == 8) {
            InvoiceNo = String.format("%08d", newSequence)
        } else if (PrefixLength == 9) {
            InvoiceNo = String.format("%09d", newSequence)
        }

        return InvoiceNo
    }

    def showGeneratedInvoiceIncome() {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=8")
        def Prefix = PrefixDataArr[0][0]
        return Prefix
    }

    def getGeneratedProductCode() {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=3")
        def Prefix = PrefixDataArr[0][0]
        def PrefixLength = PrefixDataArr[0][1]
        ///////////////////MAX PRODUCT ID/////
        def ProductDataArr = new BudgetViewDatabaseService().executeQuery("SELECT MAX(id) FROM bv.ProductMaster")

        def newSequence
        if (ProductDataArr[0][0]) {
            newSequence = ProductDataArr[0][0] + 1
        } else {
            newSequence = 1
        }
        def ProductCode
        if (PrefixLength == 2) {
            ProductCode = String.format("%02d", newSequence)
        } else if (PrefixLength == 3) {
            ProductCode = String.format("%03d", newSequence)
        } else if (PrefixLength == 4) {
            ProductCode = String.format("%04d", newSequence)
        } else if (PrefixLength == 5) {
            ProductCode = String.format("%05d", newSequence)
        } else if (PrefixLength == 6) {
            ProductCode = String.format("%06d", newSequence)
        } else if (PrefixLength == 7) {
            ProductCode = String.format("%07d", newSequence)
        } else if (PrefixLength == 8) {
            ProductCode = String.format("%08d", newSequence)
        } else if (PrefixLength == 9) {
            ProductCode = String.format("%09d", newSequence)
        }

        return ProductCode
    }

    def showGeneratedProductCode() {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=3")
        def Prefix = PrefixDataArr[0][0]
        return Prefix
    }

    def getMonthDropDown(returnIndex, selectIndex = 1, defaultSelection = 0) {
        def g = new ValidationTagLib()
        if (selectIndex == "") {
            Date now = new Date()
            selectIndex = now.month + 1
        }

        String dropDown = "<select id='" + returnIndex + "' name='" + returnIndex + "' style=\"\">"
        if (defaultSelection == 1) {
            if (selectIndex == 0) {
                dropDown += "<option value='0' selected='selected'>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
//Select Month
            } else {
                dropDown += "<option value='0'>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
                //Select Month
            }
        }
        //println(selectIndex)
        if (selectIndex == 1) {
            dropDown += "<option value='1' selected='selected'>Jan</option>"
        } else {
            dropDown += "<option value='1'>Jan</option>"
        }
        if (selectIndex == 2) {
            dropDown += "<option value='2' selected='selected'>Feb</option>"
        } else {
            dropDown += "<option value='2'>Feb</option>"
        }
        if (selectIndex == 3) {
            dropDown += "<option value='3' selected='selected'>Mar</option>"
        } else {
            dropDown += "<option value='3'>Mar</option>"
        }
        if (selectIndex == 4) {
            dropDown += "<option value='4' selected='selected'>Apr</option>"
        } else {
            dropDown += "<option value='4'>Apr</option>"
        }
        if (selectIndex == 5) {
            dropDown += "<option value='5' selected='selected'>May</option>"
        } else {
            dropDown += "<option value='5'>May</option>"
        }
        if (selectIndex == 6) {
            dropDown += "<option value='6' selected='selected'>Jun</option>"
        } else {
            dropDown += "<option value='6'>Jun</option>"
        }
        if (selectIndex == 7) {
            dropDown += "<option value='7' selected='selected'>Jul</option>"
        } else {
            dropDown += "<option value='7'>Jul</option>"
        }
        if (selectIndex == 8) {
            dropDown += "<option value='8' selected='selected'>Aug</option>"
        } else {
            dropDown += "<option value='8'>Aug</option>"
        }
        if (selectIndex == 9) {
            dropDown += "<option value='9' selected='selected'>Sep</option>"
        } else {
            dropDown += "<option value='9'>Sep</option>"
        }
        if (selectIndex == 10) {
            dropDown += "<option value='10' selected='selected'>Oct</option>"
        } else {
            dropDown += "<option value='10'>Oct</option>"
        }
        if (selectIndex == 11) {
            dropDown += "<option value='11' selected='selected'>Nov</option>"
        } else {
            dropDown += "<option value='11'>Nov</option>"
        }
        if (selectIndex == 12) {
            dropDown += "<option value='12' selected='selected'>Dec</option>"
        } else {
            dropDown += "<option value='12'>Dec</option>"
        }

        dropDown += "</select>"

        return dropDown
    }

    //polash
    //TODO: Need to fix this function.
    def getMonthDropDownForExpInv(def vendorIdForChange, def bookingPeriod) {
        def g = new ValidationTagLib()
        def defaultSelection = 0;
        String dropDown = "<select required=\"\" id='bookingPeriodStartMonthForChange' name='bookingPeriodStartMonthForChange' style=\"width: 68px; height: 30.5px;\">"
//        dropDown += "<option value='0'>Select</option>"
//        println(params.vendorIdForChange);
//        if(click == "2"){
//            println(vendorIdForChange);

        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)
        String fiscalYearId = FiscalYearInfo[0][4]

        def tmpVendorIdForChange = vendorIdForChange.toString();
        def splitted = tmpVendorIdForChange.split("::")
        def venIdForChange = splitted[0]

        def bookingPeriodArr = new BudgetViewDatabaseService().executeQuery("SELECT booking_period_start_month " +
                "FROM budget_item_expense where booking_period_start_year = '" + fiscalYearId + "' AND vendor_id = '" + venIdForChange + "' " +
                "group BY booking_period_start_month")

        for (int i = 0; i < bookingPeriodArr.size(); i++) {
            def month = bookingPeriodArr[i][0]
            if (month == 1) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='1' selected >Jan</option>"
                } else {
                    dropDown += "<option value='1'>Jan</option>"
                }

            } else if (month == 2) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='2' selected >Feb</option>"
                } else {
                    dropDown += "<option value='2'>Feb</option>"
                }
            } else if (month == 3) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='3' selected >Mar</option>"
                } else {
                    dropDown += "<option value='3'>Mar</option>"
                }
            } else if (month == 4) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='4' selected >Apr</option>"
                } else {
                    dropDown += "<option value='4'>Apr</option>"
                }

            } else if (month == 5) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='5' selected >May</option>"
                } else {
                    dropDown += "<option value='5'>May</option>"
                }
            } else if (month == 6) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='6' selected >Jun</option>"
                } else {
                    dropDown += "<option value='6'>Jun</option>"
                }
            } else if (month == 7) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='7' selected >Jul</option>"
                } else {
                    dropDown += "<option value='7'>Jul</option>"
                }
            } else if (month == 8) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='8' selected >Aug</option>"
                } else {
                    dropDown += "<option value='8'>Aug</option>"
                }

            } else if (month == 9) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='9' selected >Sep</option>"
                } else {
                    dropDown += "<option value='9'>Sep</option>"
                }

            } else if (month == 10) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='10' selected >Oct</option>"
                } else {
                    dropDown += "<option value='10'>Oct</option>"
                }
            } else if (month == 11) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='11' selected >Nov</option>"
                } else {
                    dropDown += "<option value='11'>Nov</option>"
                }
            } else if (month == 12) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='12' selected >Dec</option>"
                } else {
                    dropDown += "<option value='12'>Dec</option>"
                }

            }

        }

//        }

        dropDown += "</select>"

        return dropDown
    }

    def getBookingPeriodForExpInv(def vendorIdForChange, def bookingPeriod) {
        def g = new ValidationTagLib()
        def defaultSelection = 0;
        String dropDown = "<select required=\"\" id='bookingPeriodStartMonthForChange' onchange=\"selectedPeriod(this.value)\"  name='bookingPeriodStartMonthForChange' style=\"width: 68px; height: 30.5px;\">"

        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)
        String fiscalYearId = FiscalYearInfo[0][4]

        def tmpVendorIdForChange = vendorIdForChange.toString();
        def splitted = tmpVendorIdForChange.split("::")
        def venIdForChange = splitted[0]

        def bookingPeriodArr = new BudgetViewDatabaseService().executeQuery("SELECT booking_period_start_month " +
                "FROM budget_item_expense where booking_period_start_year = '" + fiscalYearId + "' AND vendor_id = '" + venIdForChange + "' " +
                "group BY booking_period_start_month")

        for (int i = 0; i < bookingPeriodArr.size(); i++) {
            def month = bookingPeriodArr[i][0]
            if (month == 1) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='1' selected >Jan</option>"
                } else {
                    dropDown += "<option value='1'>Jan</option>"
                }

            } else if (month == 2) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='2' selected >Feb</option>"
                } else {
                    dropDown += "<option value='2'>Feb</option>"
                }
            } else if (month == 3) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='3' selected >Mar</option>"
                } else {
                    dropDown += "<option value='3'>Mar</option>"
                }
            } else if (month == 4) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='4' selected >Apr</option>"
                } else {
                    dropDown += "<option value='4'>Apr</option>"
                }

            } else if (month == 5) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='5' selected >May</option>"
                } else {
                    dropDown += "<option value='5'>May</option>"
                }
            } else if (month == 6) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='6' selected >Jun</option>"
                } else {
                    dropDown += "<option value='6'>Jun</option>"
                }
            } else if (month == 7) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='7' selected >Jul</option>"
                } else {
                    dropDown += "<option value='7'>Jul</option>"
                }
            } else if (month == 8) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='8' selected >Aug</option>"
                } else {
                    dropDown += "<option value='8'>Aug</option>"
                }

            } else if (month == 9) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='9' selected >Sep</option>"
                } else {
                    dropDown += "<option value='9'>Sep</option>"
                }

            } else if (month == 10) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='10' selected >Oct</option>"
                } else {
                    dropDown += "<option value='10'>Oct</option>"
                }
            } else if (month == 11) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='11' selected >Nov</option>"
                } else {
                    dropDown += "<option value='11'>Nov</option>"
                }
            } else if (month == 12) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='12' selected >Dec</option>"
                } else {
                    dropDown += "<option value='12'>Dec</option>"
                }

            }

        }

//        }

        dropDown += "</select>"

        return dropDown
    }


    def getMonthDropDownForIncInv(def customerIdForChange, def bookingPeriod) {
        def g = new ValidationTagLib()
        def defaultSelection = 0;
        String dropDown = "<select required=\"\" id='bookingPeriodStartMonthForChange' name='bookingPeriodStartMonthForChange' style=\"width: 68px; height: 30.5px;\">"
//        dropDown += "<option value='0'>Select</option>"
//        println(params.vendorIdForChange);
        //if(click == "2"){
        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)
        String fiscalYearId = FiscalYearInfo[0][4]

        def tmpCustomerIdForChange = customerIdForChange.toString();
        def splitted = tmpCustomerIdForChange.split("::")
        def cusIdForChange = splitted[0]

        def bookingPeriodArr = new BudgetViewDatabaseService().executeQuery("SELECT booking_period_start_month " +
                "FROM budget_item_income where booking_period_start_year = '" + fiscalYearId + "' AND customer_id = '" + cusIdForChange + "' " +
                "GROUP BY booking_period_start_month")

        for (int i = 0; i < bookingPeriodArr.size(); i++) {
            def month = bookingPeriodArr[i][0]
            if (month == 1) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='1' selected>Jan</option>"
                } else {
                    dropDown += "<option value='1'>Jan</option>"
                }
            } else if (month == 2) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='2' selected >Feb</option>"
                } else {
                    dropDown += "<option value='2'>Feb</option>"
                }
            } else if (month == 3) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='3' selected >Mar</option>"
                } else {
                    dropDown += "<option value='3'>Mar</option>"
                }

            } else if (month == 4) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='4' selected>Apr</option>"
                } else {
                    dropDown += "<option value='4'>Apr</option>"
                }

            } else if (month == 5) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='5' selected>May</option>"
                } else {
                    dropDown += "<option value='5'>May</option>"
                }
            } else if (month == 6) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='6' selected>Jun</option>"
                } else {
                    dropDown += "<option value='6'>Jun</option>"
                }
            } else if (month == 7) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='7' selected>Jul</option>"
                } else {
                    dropDown += "<option value='7'>Jul</option>"
                }
            } else if (month == 8) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='8' selected>Aug</option>"
                } else {
                    dropDown += "<option value='8'>Aug</option>"
                }
            } else if (month == 9) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='9' selected>Sep</option>"
                } else {
                    dropDown += "<option value='9'>Sep</option>"
                }
            } else if (month == 10) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='10' selected>Oct</option>"
                } else {
                    dropDown += "<option value='10'>Oct</option>"
                }
            } else if (month == 11) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='11' selected>Nov</option>"
                } else {
                    dropDown += "<option value='11'>Nov</option>"
                }
            } else if (month == 12) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='12' selected>Dec</option>"
                } else {
                    dropDown += "<option value='12'>Dec</option>"
                }
            }

        }

        //}

        dropDown += "</select>"

        return dropDown
    }


    def getBookingPeriodForIncInv(def customerIdForChange, def bookingPeriod) {
        def g = new ValidationTagLib()
        def defaultSelection = 0;
        String dropDown = "<select required=\"\" id='bookingPeriodStartMonthForChange' onchange=\"selectedPeriod(this.value)\" name='bookingPeriodStartMonthForChange' style=\"width: 68px; height: 30.5px;\">"

        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)
        String fiscalYearId = FiscalYearInfo[0][4]

        def tmpCustomerIdForChange = customerIdForChange.toString();
        def splitted = tmpCustomerIdForChange.split("::")
        def cusIdForChange = splitted[0]

        def bookingPeriodArr = new BudgetViewDatabaseService().executeQuery("SELECT booking_period_start_month " +
                "FROM budget_item_income where booking_period_start_year = '" + fiscalYearId + "' AND customer_id = '" + cusIdForChange + "' " +
                "GROUP BY booking_period_start_month")

        for (int i = 0; i < bookingPeriodArr.size(); i++) {
            def month = bookingPeriodArr[i][0]
            if (month == 1) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='1' selected>Jan</option>"
                } else {
                    dropDown += "<option value='1'>Jan</option>"
                }
            } else if (month == 2) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='2' selected >Feb</option>"
                } else {
                    dropDown += "<option value='2'>Feb</option>"
                }
            } else if (month == 3) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='3' selected >Mar</option>"
                } else {
                    dropDown += "<option value='3'>Mar</option>"
                }

            } else if (month == 4) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='4' selected>Apr</option>"
                } else {
                    dropDown += "<option value='4'>Apr</option>"
                }

            } else if (month == 5) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='5' selected>May</option>"
                } else {
                    dropDown += "<option value='5'>May</option>"
                }
            } else if (month == 6) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='6' selected>Jun</option>"
                } else {
                    dropDown += "<option value='6'>Jun</option>"
                }
            } else if (month == 7) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='7' selected>Jul</option>"
                } else {
                    dropDown += "<option value='7'>Jul</option>"
                }
            } else if (month == 8) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='8' selected>Aug</option>"
                } else {
                    dropDown += "<option value='8'>Aug</option>"
                }
            } else if (month == 9) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='9' selected>Sep</option>"
                } else {
                    dropDown += "<option value='9'>Sep</option>"
                }
            } else if (month == 10) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='10' selected>Oct</option>"
                } else {
                    dropDown += "<option value='10'>Oct</option>"
                }
            } else if (month == 11) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='11' selected>Nov</option>"
                } else {
                    dropDown += "<option value='11'>Nov</option>"
                }
            } else if (month == 12) {
                if (Integer.parseInt(bookingPeriod) == month) {
                    dropDown += "<option value='12' selected>Dec</option>"
                } else {
                    dropDown += "<option value='12'>Dec</option>"
                }
            }

        }

        //}

        dropDown += "</select>"

        return dropDown
    }

    def getMonthDropDownForPrivateProcess(def budgetMasterId, def bookingPeriod, def privateSpendingId) {
        def g = new ValidationTagLib()
        def defaultSelection = 0;
        String dropDown = "<select required=\"\" id='bookingPeriod_${privateSpendingId}' name='bookingPeriod_${privateSpendingId}' style=\"width: 68px; height: 30.5px;\">"
//        dropDown += "<option value='0'>Select</option>"
//        println(params.vendorIdForChange);
        //if(click == "2"){
        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)
        String fiscalYear = FiscalYearInfo[0][4]


        def bookingPeriodArr = new BudgetViewDatabaseService().executeQuery("""SELECT booking_period from
            private_budget_item WHERE booking_year = '${fiscalYear}' and budget_name_id = '${
            budgetMasterId
        }' GROUP BY booking_period """)

        for (int i = 0; i < bookingPeriodArr.size(); i++) {
            def month = bookingPeriodArr[i][0]
            if (month == 1) {
                if (bookingPeriod == month) {
                    dropDown += "<option value='1' selected>Jan</option>"
                } else {
                    dropDown += "<option value='1'>Jan</option>"
                }
            } else if (month == 2) {
                if (bookingPeriod == month) {
                    dropDown += "<option value='2' selected >Feb</option>"
                } else {
                    dropDown += "<option value='2'>Feb</option>"
                }
            } else if (month == 3) {
                if (bookingPeriod == month) {
                    dropDown += "<option value='3' selected >Mar</option>"
                } else {
                    dropDown += "<option value='3'>Mar</option>"
                }

            } else if (month == 4) {
                if (bookingPeriod == month) {
                    dropDown += "<option value='4' selected>Apr</option>"
                } else {
                    dropDown += "<option value='4'>Apr</option>"
                }

            } else if (month == 5) {
                if (bookingPeriod == month) {
                    dropDown += "<option value='5' selected>May</option>"
                } else {
                    dropDown += "<option value='5'>May</option>"
                }
            } else if (month == 6) {
                if (bookingPeriod == month) {
                    dropDown += "<option value='6' selected>Jun</option>"
                } else {
                    dropDown += "<option value='6'>Jun</option>"
                }
            } else if (month == 7) {
                if (bookingPeriod == month) {
                    dropDown += "<option value='7' selected>Jul</option>"
                } else {
                    dropDown += "<option value='7'>Jul</option>"
                }
            } else if (month == 8) {
                if (bookingPeriod == month) {
                    dropDown += "<option value='8' selected>Aug</option>"
                } else {
                    dropDown += "<option value='8'>Aug</option>"
                }
            } else if (month == 9) {
                if (bookingPeriod == month) {
                    dropDown += "<option value='9' selected>Sep</option>"
                } else {
                    dropDown += "<option value='9'>Sep</option>"
                }
            } else if (month == 10) {
                if (bookingPeriod == month) {
                    dropDown += "<option value='10' selected>Oct</option>"
                } else {
                    dropDown += "<option value='10'>Oct</option>"
                }
            } else if (month == 11) {
                if (bookingPeriod == month) {
                    dropDown += "<option value='11' selected>Nov</option>"
                } else {
                    dropDown += "<option value='11'>Nov</option>"
                }
            } else if (month == 12) {
                if (bookingPeriod == month) {
                    dropDown += "<option value='12' selected>Dec</option>"
                } else {
                    dropDown += "<option value='12'>Dec</option>"
                }
            }

        }

        dropDown += "</select>"

        return dropDown
    }

    def getMonthDropDownForReservationInvoice(def budgetMasterId, def bookingPeriod, def reservationSpendingId) {
        def g = new ValidationTagLib()
        def defaultSelection = 0;
        String dropDown = "<select required=\"\" id='bookingPeriod_${reservationSpendingId}' name='bookingPeriod_${reservationSpendingId}' style=\"width: 68px; height: 30.5px;\">"
//        dropDown += "<option value='0'>Select</option>"
//        println(params.vendorIdForChange);
        //if(click == "2"){
        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)
        String fiscalYear = FiscalYearInfo[0][4]


        def bookingPeriodArr = new BudgetViewDatabaseService().executeQuery("""SELECT booking_period_month from reservation_budget_item
                        WHERE booking_period_year = '${fiscalYear}' and budget_name_id = '${
            budgetMasterId
        }' GROUP BY booking_period_month""")

        for (int i = 0; i < bookingPeriodArr.size(); i++) {
            def month = bookingPeriodArr[i][0]
            if (month == 1) {
                if (bookingPeriod == month) {
                    dropDown += "<option value='1' selected>Jan</option>"
                } else {
                    dropDown += "<option value='1'>Jan</option>"
                }
            } else if (month == 2) {
                if (bookingPeriod == month) {
                    dropDown += "<option value='2' selected >Feb</option>"
                } else {
                    dropDown += "<option value='2'>Feb</option>"
                }
            } else if (month == 3) {
                if (bookingPeriod == month) {
                    dropDown += "<option value='3' selected >Mar</option>"
                } else {
                    dropDown += "<option value='3'>Mar</option>"
                }

            } else if (month == 4) {
                if (bookingPeriod == month) {
                    dropDown += "<option value='4' selected>Apr</option>"
                } else {
                    dropDown += "<option value='4'>Apr</option>"
                }

            } else if (month == 5) {
                if (bookingPeriod == month) {
                    dropDown += "<option value='5' selected>May</option>"
                } else {
                    dropDown += "<option value='5'>May</option>"
                }
            } else if (month == 6) {
                if (bookingPeriod == month) {
                    dropDown += "<option value='6' selected>Jun</option>"
                } else {
                    dropDown += "<option value='6'>Jun</option>"
                }
            } else if (month == 7) {
                if (bookingPeriod == month) {
                    dropDown += "<option value='7' selected>Jul</option>"
                } else {
                    dropDown += "<option value='7'>Jul</option>"
                }
            } else if (month == 8) {
                if (bookingPeriod == month) {
                    dropDown += "<option value='8' selected>Aug</option>"
                } else {
                    dropDown += "<option value='8'>Aug</option>"
                }
            } else if (month == 9) {
                if (bookingPeriod == month) {
                    dropDown += "<option value='9' selected>Sep</option>"
                } else {
                    dropDown += "<option value='9'>Sep</option>"
                }
            } else if (month == 10) {
                if (bookingPeriod == month) {
                    dropDown += "<option value='10' selected>Oct</option>"
                } else {
                    dropDown += "<option value='10'>Oct</option>"
                }
            } else if (month == 11) {
                if (bookingPeriod == month) {
                    dropDown += "<option value='11' selected>Nov</option>"
                } else {
                    dropDown += "<option value='11'>Nov</option>"
                }
            } else if (month == 12) {
                if (bookingPeriod == month) {
                    dropDown += "<option value='12' selected>Dec</option>"
                } else {
                    dropDown += "<option value='12'>Dec</option>"
                }
            }

        }

        dropDown += "</select>"

        return dropDown
    }


    def getYearDropDown(returnIndex, startYear, EndYear, selectIndex = '', defaultSelection = 0) {

        def g = new ValidationTagLib()
        def tempSelectedYear = ""
        def selectedYear = 0
        String tempSelect = selectIndex.toString()
        if (selectIndex) {

        } else {
            Date now = new Date()
            selectIndex = now.year + 1900
        }

        if (tempSelect.contains("-")) {
            tempSelectedYear = selectIndex.substring(0, 4)
            selectedYear = Integer.parseInt(tempSelectedYear)

        } else {
            selectedYear = selectIndex
        }
        String dropDown = "<select id='" + returnIndex + "' name='" + returnIndex + "' style=\"\">"
        if (defaultSelection == 1) {
            if (selectIndex == 0) {
                dropDown += "<option value='0' selected='selected'>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
                //Select Year
            } else {
                dropDown += "<option value='0' selected='selected'>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
            }
        }
        def interVal = 1
        def i = 0
        for (i = startYear; i <= EndYear; i = i + interVal) {
            if (Integer.parseInt(selectedYear.toString()) == i) {
                dropDown += "<option value='" + i + "' selected='selected'>" + i + "</option>"
            } else {
                dropDown += "<option value='" + i + "'>" + i + "</option>"
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    /*
    * This function is generating multiple vendor list where byShop status = 1
    * */

    def getVendorMultipleDropDown(returnIndex, selectIndex = 0) {

        def VendorArr = new BudgetViewDatabaseService().executeQuery("SELECT id,vendorName,vendorCode FROM VendorMaster where status=1")

        def vendorPrefix = showGeneratedVendorCode()


        String dropDown = "<select name='" + returnIndex + "' >"
        if (VendorArr.size()) {

            int tem = 0

            for (int i = 0; i < VendorArr.size(); i++) {

                if (selectIndex == VendorArr[i][0]) {
                    dropDown += "<option value='" + VendorArr[i][0] + "' selected>" + VendorArr[i][1] + " [" + vendorPrefix + VendorArr[i][2] + "]</option>"
                } else {
                    dropDown += "<option value='" + VendorArr[i][0] + "' >" + VendorArr[i][1] + " [" + vendorPrefix + VendorArr[i][2] + "]</option>"
                }

            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getOnlyVendorMultipleDropDown(returnIndex, selectIndex = 0) {

        def VendorArr = new BudgetViewDatabaseService().executeQuery("SELECT id,vendorName,vendorCode FROM VendorMaster where status=1 AND byShop!=1 AND vendorType!='sn'")

        def vendorPrefix = showGeneratedVendorCode()


        String dropDown = "<select name='" + returnIndex + "' >"
        if (VendorArr.size()) {
            int tem = 0
            for (int i = 0; i < VendorArr.size(); i++) {

                if (selectIndex == VendorArr[i][0]) {
                    dropDown += "<option value='" + VendorArr[i][0] + "' selected>" + VendorArr[i][1] + " [" + vendorPrefix + VendorArr[i][2] + "]</option>"
                } else {
                    dropDown += "<option value='" + VendorArr[i][0] + "' >" + VendorArr[i][1] + " [" + vendorPrefix + VendorArr[i][2] + "]</option>"
                }

            }
        }
        dropDown += "</select>"
        return dropDown
    }


    def getCustomerForIncomeBudgetItemDropDown(returnIndex, selectIndex = 0) {
        def g = new ValidationTagLib()
        def CustomerArr = new BudgetViewDatabaseService().executeQuery("select customer_name,id from customer_master where status=1 ORDER BY customer_name ASC")

        String dropDown = "<select required onchange='onchangeBudgetName()' name='" + returnIndex + "'id='" + returnIndex + "' >"
        dropDown += "<option value=''>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
        if (CustomerArr.size()) {

            for (int i = 0; i < CustomerArr.size(); i++) {

                if (selectIndex == CustomerArr[i][1]) {
                    dropDown += "<option value='" + CustomerArr[i][1] + "' selected>" + CustomerArr[i][0] + "</option>"
                } else {
                    dropDown += "<option value='" + CustomerArr[i][1] + "' >" + CustomerArr[i][0] + "</option>"
                }

            }
        }
        dropDown += "</select>"
        return dropDown
    }


    def getVendorForBudgetItemDropDown(returnIndex, selectIndex = 0) {
        def g = new ValidationTagLib()
        def vendorArr = new BudgetViewDatabaseService().executeQuery("select vendor_name,id from vendor_master where status=1  AND vendor_type!='sn' AND vendor_type!='rp' ORDER BY vendor_name ASC")

        String dropDown = "<select required onchange='onchangeBudgetName()' name='" + returnIndex + "'id='" + returnIndex + "' >"
        dropDown += "<option value=''>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
        if (vendorArr.size()) {

            for (int i = 0; i < vendorArr.size(); i++) {

                if (selectIndex == vendorArr[i][1]) {
                    dropDown += "<option value='" + vendorArr[i][1] + "' selected>" + vendorArr[i][0] + "</option>"
                } else {
                    dropDown += "<option value='" + vendorArr[i][1] + "' >" + vendorArr[i][0] + "</option>"
                }

            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getReservationForBudgetItemDropDown(returnIndex, selectIndex = 0) {
        def g = new ValidationTagLib()
        def reservationArr = new BudgetViewDatabaseService().executeQuery("SELECT id, reservation_name from reservation_budget_master where status=1 ORDER BY reservation_name")

        String dropDown = "<select required onchange='getGLAccount(this.value)' name='" + returnIndex + "'id='" + returnIndex + "' >"
        dropDown += "<option value=''>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
        if (reservationArr.size()) {

            for (int i = 0; i < reservationArr.size(); i++) {

                if (selectIndex == reservationArr[i][0]) {
                    dropDown += "<option value='" + reservationArr[i][0] + "' selected>" + reservationArr[i][1] + "</option>"
                } else {
                    dropDown += "<option value='" + reservationArr[i][0] + "' >" + reservationArr[i][1] + "</option>"
                }

            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getPrivateForBudgetItemDropDown(returnIndex, selectIndex = 0) {
        def g = new ValidationTagLib()
        def privateArr = new BudgetViewDatabaseService().executeQuery("SELECT budget_name, id FROM private_budget_master where status=1 ORDER BY budget_name")

        String dropDown = "<select required onchange='getPrivateBudgetType(this.value)' name='" + returnIndex + "'id='" + returnIndex + "' >"
        dropDown += "<option value=''>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
        if (privateArr.size()) {

            for (int i = 0; i < privateArr.size(); i++) {

                if (selectIndex == privateArr[i][1]) {
                    dropDown += "<option value='" + privateArr[i][1] + "' selected>" + privateArr[i][0] + "</option>"
                } else {
                    dropDown += "<option value='" + privateArr[i][1] + "' >" + privateArr[i][0] + "</option>"
                }

            }
        }
        dropDown += "</select>"
        return dropDown
    }

    /*
   * This function is generating multiple various vendor list where byShop status = 1
   * */

    def getVendorMultipleDropDownForVariusVendor(returnIndex, selectIndex = 0) {

        def VendorArr = new BudgetViewDatabaseService().executeQuery("SELECT id,vendorName,vendorCode FROM VendorMaster where status=1")

        def vendorPrefix = showGeneratedVendorCode()


        String dropDown = "<select name='" + returnIndex + "' >"
        if (VendorArr.size()) {

            int tem = 0

            for (int i = 0; i < VendorArr.size(); i++) {

                if (selectIndex == VendorArr[i][0]) {
                    dropDown += "<option value='" + VendorArr[i][0] + "' selected>" + VendorArr[i][1] + " [" + vendorPrefix + VendorArr[i][2] + "]</option>"
                } else {
                    dropDown += "<option value='" + VendorArr[i][0] + "' >" + VendorArr[i][1] + " [" + vendorPrefix + VendorArr[i][2] + "]</option>"
                }

            }
        }
        dropDown += "</select>"
        return dropDown
    }

    /*
   * Get Multiple Customer
   * */

    def getCustomerMultipleDropDown(returnIndex, selectIndex = 0) {
        def CustomerArr = new BudgetViewDatabaseService().executeQuery("SELECT id,customerName,customerCode FROM CustomerMaster where status=1")
        def customerPrefix = showGeneratedCustomerCode()

        int tem = 0
        /*String dropDown = "<select name='" + returnIndex + "' multiple style=\"height: 62px;\">"*/
        String dropDown = "<select name='" + returnIndex + "' >"
        if (CustomerArr.size()) {
            for (int i = 0; i < CustomerArr.size(); i++) {
                //if (tem == i) {
                if (selectIndex == CustomerArr[i][0]) {
                    dropDown += "<option value='" + CustomerArr[i][0] + "' selected>" + CustomerArr[i][1] + " [" + customerPrefix + CustomerArr[i][2] + "]</option>"
                } else {
                    dropDown += "<option value='" + CustomerArr[i][0] + "' >" + CustomerArr[i][1] + " [" + customerPrefix + CustomerArr[i][2] + "]</option>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    /*
    * Manual Reconciliation dropCustomerList
    * */

    def getCustomerDropDownListForReconciliation(returnIndex, selectIndex = 0, contextCustomer = "") {
        LinkedHashMap gridResult
        String select = "id,customerName,customerCode"
        String selectIndexes = "id,customerName,customerCode"
        String from = "CustomerMaster"
        String where = "status='1'"
        String orderBy = "id ASC"
        gridResult = new BudgetViewDatabaseService().select(select, from, where, orderBy, '', 'true', selectIndexes)

        def CustomerArr = gridResult['dataGridList']
        //def CustomerArr = new BudgetViewDatabaseService().executeQuery("SELECT id,customerName,customerCode FROM CustomerMaster where status=1")

        def customerPrefix = showGeneratedCustomerCode()
        int tem = 0
        String dropDown = "<select id=\"customerId\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BudgetItemIncomeDetails/selectCustomerRelatedInformation',success:function(data,textStatus){jQuery('#customerbudgetresults').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"customerId\">"

        if (CustomerArr.size()) {
            for (int i = 0; i < CustomerArr.size(); i++) {
                //if (tem == i) {
                if (selectIndex == CustomerArr[i][0]) {
                    dropDown += "<option value='" + CustomerArr[i][0] + "' selected>" + CustomerArr[i][1] + " [" + customerPrefix + CustomerArr[i][2] + "]</option>"
                } else {
                    dropDown += "<option value='" + CustomerArr[i][0] + "' >" + CustomerArr[i][1] + " [" + customerPrefix + CustomerArr[i][2] + "]</option>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }


    def getCustomerAndVendorDropDownListForReconciliation(returnIndex, selectIndex = 0, contextCustomer = "", grandTotal = 0.00, companyBankAccNo = "", selectedByBankAccountNo) {

        LinkedHashMap gridResult
        def customerPrefix = showGeneratedCustomerCode()
        def vendorPrefix = showGeneratedVendorCode()

        def string_customer = "SELECT ii.customer_id,cm.customer_name,cm.customer_code," +
                "ii.id,ROUND(SUM(tm.amount),2) as remainAmount FROM invoice_income AS ii " +
                "INNER JOIN customer_master AS cm ON (ii.customer_id=cm.id) " +
                "INNER JOIN trans_master tm ON tm.recenciliation_code = CONCAT(ii.id,'#1') AND " +
                "tm.account_code=(Select debitor_gl_code from debit_credit_gl_setup) " +
                "GROUP BY ii.customer_id ORDER BY cm.customer_name ASC"

        def CustomerArr = new BudgetViewDatabaseService().executeQuery(string_customer)
        def string_vendor = "SELECT ie.vendor_id,vm.vendor_name,vm.vendor_code," +
                "ie.id,abs(ROUND(SUM(tm.amount),2)) as remainAmount FROM invoice_expense AS ie " +
                "INNER JOIN vendor_master AS vm ON (ie.vendor_id=vm.id) " +
                "INNER JOIN trans_master tm ON (tm.recenciliation_code = CONCAT(ie.id,'#2') OR tm.recenciliation_code = CONCAT(ie.id,'#4')) " +
                "AND tm.account_code=(Select creditor_gl_code from debit_credit_gl_setup) " +
                "GROUP BY ie.vendor_id ORDER BY vm.vendor_name ASC ";

        def VendorArr = new BudgetViewDatabaseService().executeQuery(string_vendor)
        def g = new ValidationTagLib()

        //Populating list
        String dropDown = "<select id=\"customerOrVendorId\"  onchange=\"reconciliationVendorCustomerinfo();\" name=\"customerOrVendorId\">"

        dropDown += "<option value='" + "CUS_0" + "_" + Math.abs(grandTotal) + "' >" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
        if (CustomerArr.size()) {
            dropDown += "<option style=\"background-color:  #eaeae1\" value='" + "allCus" +"_" +companyBankAccNo+ "_"+Math.abs(grandTotal)+"' >"+g.message(code:'com.common.allCustomer.label')+"</option>"
            for (int i = 0; i < CustomerArr.size(); i++) {
                String strValue = (CustomerArr[i][4]).toString();
                double fRemainAmount = Double.parseDouble(strValue);
                if (Math.abs(fRemainAmount) != 0.0) {
                    dropDown += "<option value='" + "CUS_" + CustomerArr[i][0] + "_" + Math.abs(grandTotal) + "_" + companyBankAccNo + "_" + selectedByBankAccountNo + "' >" + CustomerArr[i][1] + " [" + customerPrefix + CustomerArr[i][2] + "]</option>"
                }
            }
        } else {
            dropDown += "<option>" + g.message(code: 'coreParamsHelper.noCustomerDataAvailable.label') + "</option>"
        }

        if (VendorArr.size()) {
            dropDown += "<option style=\"background-color:  #eaeae1\" value='" + "allVen" +"_" +companyBankAccNo+"_" + Math.abs(grandTotal) + "' >"+g.message(code: 'com.common.allVendor.label')+"</option>"
            for (int i = 0; i < VendorArr.size(); i++) {
                String strValue = (VendorArr[i][4]).toString();
                double fRemainAmount = Double.parseDouble(strValue);
                BigDecimal bdRemainAmount = new BigDecimal(fRemainAmount)
                fRemainAmount = bdRemainAmount.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()
                if (Math.abs(fRemainAmount) != 0.0) {
                    dropDown += "<option value='" + "VEN_" + VendorArr[i][0] + "_" + Math.abs(grandTotal) + "_" + companyBankAccNo + "_" + selectedByBankAccountNo + "' >" + VendorArr[i][1] + " [" + vendorPrefix + VendorArr[i][2] + "]</option>"
                }
            }
        } else {
            dropDown += "<option>" + g.message(code: 'coreParamsHelper.noVendorDataAvailable.label') + "</option>"
        }

        dropDown += "</select>"
        return dropDown

    }

    def getCustomerDropDownForIncomeAllBudget(returnIndex, selectIndex = 0, contextCustomer = "", customerType = "") {
        LinkedHashMap gridResult
        String select = "id,customerName,customerCode"
        String selectIndexes = "id,customerName,customerCode"
        String from = "CustomerMaster"
        String where = "status='1'"
        if (customerType) {
            where = "status='1' AND customer_type='" + customerType + "' ";
        }
        String orderBy = "customerName ASC"
        gridResult = new BudgetViewDatabaseService().select(select, from, where, orderBy, '', 'true', selectIndexes)

        def customerArr = gridResult['dataGridList']
        def g = new ValidationTagLib()

        def customerPrefix = showGeneratedCustomerCode()

        String dropDown = "<select id=\"customerId\" required=\"\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BudgetItemIncomeDetails/selectCustomerRelatedInformation',success:function(data,textStatus){jQuery('#customerbudgetresults').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"customerId\" style=\"float:left;\">"
        dropDown += "<option value=\"\" selected>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"

        if (customerArr.size()) {
            int tem = 0
            for (int i = 0; i < customerArr.size(); i++) {

                if (selectIndex == customerArr[i][0]) {
                    dropDown += "<option value='" + customerArr[i][0] + "' selected>" + customerArr[i][1] + " [" + customerPrefix + customerArr[i][2] + "]</option>"
                } else {
                    dropDown += "<option value='" + customerArr[i][0] + "' >" + customerArr[i][1] + " [" + customerPrefix + customerArr[i][2] + "]</option>"
                }

            }
        }
        dropDown += "</select>"
        return dropDown
    }

//    Income Invoice
    def getCustomerDropDownForIncomeAllBudgetChange(returnIndex, selectIndex = 0, contextCustomer = "", bookInvoiceId = 0, editId = 0,
                                                    def fromAfterSave) {

        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)
        String fiscalYearBegin = FiscalYearInfo[0][4]
//        println("Book Invoice ID:"+bookInvoiceId)
//        println("Edit Id:"+editId)

        String strQuery = "SELECT cm.id,cm.customer_name,cm.customer_code FROM customer_master as cm  " +
                "INNER JOIN budget_item_income as bii on bii.customer_id = cm.id " +
                "WHERE bii.booking_period_start_year = '" + fiscalYearBegin + "' GROUP BY cm.id ORDER BY cm.customer_name"

        def customerArr = new BudgetViewDatabaseService().executeQuery(strQuery)
        def g = new ValidationTagLib()

        def customerPrefix = showGeneratedCustomerCode()
        String dropDown = ""
//        if(fromAfterSave == '1'){
//             dropDown = "<select id='customerIdForChange' onchange='generateBookingPeriod();' name='customerIdForChange' style='float:left;'>"
//        }else{
        dropDown = "<select id=\"customerIdForChange\" onchange=\"generateBookingPeriod();jQuery.ajax({type:'POST',data:'data=' + this.value , url:'${contextCustomer}/invoiceIncome/selectBudgetRelatedInformation',success:function(data,textStatus){jQuery('#updateItemList').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"customerIdForChange\" style=\"float:left;\">"
//        }

//        String dropDown = "<select id=\"customerIdForChange\" onchange=\"jQuery.ajax({type:'POST',data:'data=' + this.value , url:'${contextCustomer}/invoiceIncome/selectBudgetRelatedInformation',success:function(data,textStatus){jQuery('#updateItemList').html(data); /*checkBudgetItemForReceipt(2);*/},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"customerIdForChange\" style=\"float:left;\">"
        dropDown += "<option value='" + 0 + "::" + bookInvoiceId + "::" + editId + "::" + false + "'    selected>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"

        if (customerArr.size()) {
            int tem = 0
            for (int i = 0; i < customerArr.size(); i++) {
                if (Integer.parseInt(selectIndex) == customerArr[i][0]) {
                    dropDown += "<option value='" + customerArr[i][0] + "::" + bookInvoiceId + "::" + editId + "::" + true + "' selected>" + customerArr[i][1] + " [" + customerPrefix + customerArr[i][2] + "]</option>"

                } else {
                    dropDown += "<option value='" + customerArr[i][0] + "::" + bookInvoiceId + "::" + editId + "::" + true + "' >" + customerArr[i][1] + " [" + customerPrefix + customerArr[i][2] + "]</option>"
                }
            }
        }

        dropDown += "</select>"
//        println(dropDown);
        return dropDown
    }

    def getBudgetForIncome(contextCustomer) {

        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)
        String fiscalYearBegin = FiscalYearInfo[0][4]

        String strQuery = "SELECT cm.id,cm.customer_name,cm.customer_code, bii.booking_period_start_month FROM customer_master as cm  " +
                "INNER JOIN budget_item_income as bii on bii.customer_id = cm.id " +
                "WHERE bii.booking_period_start_year = '" + fiscalYearBegin + "' GROUP BY cm.id ORDER BY cm.customer_name"

        def customerArr = new BudgetViewDatabaseService().executeQuery(strQuery)
        def g = new ValidationTagLib()

        def customerPrefix = showGeneratedCustomerCode()
        String dropDown = ""
        dropDown = "<select id=\"customerIdForChange\" onchange=\"generateBookingPeriod();jQuery.ajax({type:'POST',data:'data=' + this.value , url:'${contextCustomer}/invoiceIncome/selectBudgetRelatedInformation',success:function(data,textStatus){jQuery('#updateItemList').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"customerIdForChange\" style=\"float:left;\">"

        dropDown += "<option value='" + 0 + "::" + false + "'    selected>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"

        if (customerArr.size()) {
            int tem = 0
            for (int i = 0; i < customerArr.size(); i++) {

                dropDown += "<option value='" + customerArr[i][0] + "::" + customerArr[i][3] + "::" + true + "' >" +
                        customerArr[i][1] + " [" + customerPrefix + customerArr[i][2] + "]</option>"
            }
        }

        dropDown += "</select>"
//        println(dropDown);
        return dropDown
    }

    def getBudgetForExpense(contextCustomer) {

        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)
        String fiscalYearBegin = FiscalYearInfo[0][4]

        String strQuery = "SELECT vm.id,vm.vendor_name,vm.vendor_code, bie.booking_period_start_month FROM vendor_master as vm  " +
                "INNER JOIN budget_item_expense as bie on bie.vendor_id = vm.id " +
                "WHERE bie.booking_period_start_year = '" + fiscalYearBegin + "' GROUP BY vm.id ORDER BY vm.vendor_name"

        def vendorArr = new BudgetViewDatabaseService().executeQuery(strQuery)
        def g = new ValidationTagLib()

        def vendorPrefix = showGeneratedVendorCode()
        String dropDown = ""
        dropDown = "<select id=\"vendorIdForChange\" onchange=\"generateBookingPeriod();jQuery.ajax({type:'POST',data:'data=' + this.value , url:'${contextCustomer}/invoiceExpense/selectBudgetRelatedInformation',success:function(data,textStatus){jQuery('#updateItemList').html(data); },error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"vendorIdForChange\" style=\"float:left;\">"

        dropDown += "<option value='" + 0 + "::" + false + "'    selected>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"

        if (vendorArr.size()) {
            int tem = 0
            for (int i = 0; i < vendorArr.size(); i++) {

                dropDown += "<option value='" + vendorArr[i][0] + "::" + vendorArr[i][3] + "::" + true + "' >" +
                        vendorArr[i][1] + " [" + vendorPrefix + vendorArr[i][2] + "]</option>"
            }
        }

        dropDown += "</select>"
//        println(dropDown);
        return dropDown
    }

    def getVendorDropDownForExpanseBudget(returnIndex, selectIndex = 0, contextCustomer = "") {

        def VendorArr = new BudgetViewDatabaseService().executeQuery("SELECT id,vendorName,vendorCode FROM VendorMaster where status=1 AND vendorType='vn' AND vendorType!='bn'")
        def vendorPrefix = showGeneratedVendorCode()
        String dropDown = "<select id=\"vendorids\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BudgetItemExpenseDetails/selectVendorRelatedInformation',success:function(data,textStatus){jQuery('#vendorbudgetresults').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"vendorids\">"
        if (VendorArr.size()) {
            int tem = 0
            for (int i = 0; i < VendorArr.size(); i++) {

                if (selectIndex == VendorArr[i][0]) {
                    dropDown += "<option value='" + VendorArr[i][0] + "' selected>" + VendorArr[i][1] + " [" + vendorPrefix + VendorArr[i][2] + "]</option>"
                } else {
                    dropDown += "<option value='" + VendorArr[i][0] + "' >" + VendorArr[i][1] + " [" + vendorPrefix + VendorArr[i][2] + "]</option>"
                }

            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getVendorDropDownForExpanseAllBudget(returnIndex, selectIndex = 0, contextCustomer = "", vendorType = "") {
        LinkedHashMap gridResult
        String select = "id,vendorName,vendorCode"
        String selectIndexes = "id,vendorName,vendorCode"
        String from = "VendorMaster"
        String where = "status='1' AND vendor_type!='sn' AND vendor_type!='rp'"
        if (vendorType) {
            where = "status='1' AND vendor_type='" + vendorType + "' ";
        }
        String orderBy = "vendorName ASC"
        gridResult = new BudgetViewDatabaseService().select(select, from, where, orderBy, '', 'true', selectIndexes)

        def VendorArr = gridResult['dataGridList']
        def g = new ValidationTagLib()

        //def VendorArr = new BudgetViewDatabaseService().executeQuery("SELECT id,vendorName,vendorCode FROM VendorMaster where status=1 AND vendorType='vn' OR vendorType='bn'")
        def vendorPrefix = showGeneratedVendorCode()
        String dropDown = "<select id=\"vendorids\" required=\"\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BudgetItemExpenseDetails/selectVendorRelatedInformation',success:function(data,textStatus){jQuery('#vendorbudgetresults').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"vendorids\" style=\"float: left;\">"
        dropDown += "<option value=\"\" selected>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"

        if (VendorArr.size()) {
            int tem = 0
            for (int i = 0; i < VendorArr.size(); i++) {

                if (selectIndex == VendorArr[i][0]) {
                    dropDown += "<option value='" + VendorArr[i][0] + "' selected>" + VendorArr[i][1] + " [" + vendorPrefix + VendorArr[i][2] + "]</option>"
                } else {
                    dropDown += "<option value='" + VendorArr[i][0] + "' >" + VendorArr[i][1] + " [" + vendorPrefix + VendorArr[i][2] + "]</option>"
                }

            }
        }
        dropDown += "</select>"
        return dropDown
    }

//    ********   Expense Invoice ************
    def getVendorDropDownForExpanseAllBudgetForChange(returnIndex, selectIndex = 0, bookInvoiceId = 0, editId = 0, contextcustomer, fromAfterSave) {

        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)
        String fiscalYearBegin = FiscalYearInfo[0][4]

        String strQuery = "SELECT vm.id,vm.vendor_name,vm.vendor_code FROM vendor_master as vm  " +
                "INNER JOIN budget_item_expense as bie on bie.vendor_id = vm.id " +
                "WHERE bie.booking_period_start_year = '" + fiscalYearBegin + "' GROUP BY vm.id ORDER BY vm.vendor_name"

        def VendorArr = new BudgetViewDatabaseService().executeQuery(strQuery)
        def g = new ValidationTagLib()

        def vendorPrefix = showGeneratedVendorCode()

        String dropDown
        //if(fromAfterSave == "1"){
        //  dropDown = "<select id=\"vendorIdForChange\" name=\"vendorIdForChange\" style=\"float:left;\" onchange='generateBookingPeriod();jQuery.ajax({type:'POST',data:'data=' + this.value , url:'${contextcustomer}/invoiceExpense/selectBudgetRelatedInformation',success:function(data,textStatus){jQuery('#updateItemList').html(data); },error:function(XMLHttpRequest,textStatus,errorThrown){}});'>"
        //}else{
        dropDown = "<select id=\"vendorIdForChange\" onchange=\"generateBookingPeriod();jQuery.ajax({type:'POST',data:'data=' + this.value , url:'${contextcustomer}/invoiceExpense/selectBudgetRelatedInformation',success:function(data,textStatus){jQuery('#updateItemList').html(data); },error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"vendorIdForChange\" style=\"float:left;\">"
        //}

// String dropDown = "<select id=\"vendorIdForChange\" onchange=\"jQuery.ajax({type:'POST',data:'data=' + this.value , url:'${contextcustomer}/invoiceExpense/selectBudgetRelatedInformation',success:function(data,textStatus){jQuery('#updateItemList').html(data);checkForBudgetItem();},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"vendorIdForChange\" style=\"float:left;\">"
        dropDown += "<option value='" + 0 + "::" + bookInvoiceId + "::" + editId + "::" + false + "' selected>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"


        if (VendorArr.size()) {
            int tem = 0
            for (int i = 0; i < VendorArr.size(); i++) {

                if (selectIndex == VendorArr[i][0]) {
                    dropDown += "<option value='" + VendorArr[i][0] + "::" + bookInvoiceId + "::" + editId + "::" + true + "' selected>" + VendorArr[i][1] + " [" + vendorPrefix + VendorArr[i][2] + "]</option>"
                } else {
                    dropDown += "<option value='" + VendorArr[i][0] + "::" + bookInvoiceId + "::" + editId + "::" + true + "' >" + VendorArr[i][1] + " [" + vendorPrefix + VendorArr[i][2] + "]</option>"
                }

            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getVendorDropDownForReceiptAllBudgetForChange(returnIndex, selectIndex = 0, bookInvoiceId = 0, editId = 0, contextPath, fromAfterSave) {

        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)
        String fiscalYearBegin = FiscalYearInfo[0][4]
        def a = selectIndex

        String strQuery = "SELECT vm.id,vm.vendor_name,vm.vendor_code FROM vendor_master as vm  " +
                "INNER JOIN budget_item_expense as bie on bie.vendor_id = vm.id " +
                "WHERE bie.booking_period_start_year = '" + fiscalYearBegin + "' GROUP BY vm.id ORDER BY vm.vendor_name"

        def VendorArr = new BudgetViewDatabaseService().executeQuery(strQuery)
        def g = new ValidationTagLib()
        def notCnahge

        //def VendorArr = new BudgetViewDatabaseService().executeQuery("SELECT id,vendorName,vendorCode FROM VendorMaster where status=1 AND vendorType='vn' OR vendorType='bn'")
        def vendorPrefix = showGeneratedVendorCode()
        String dropDown = ""
        //if(fromAfterSave == "1"){
        //    dropDown = "<select id=\"vendorIdForChangeReceipt\" onchange=\"checkBudgetItemForReceipt(2);\" name=\"vendorIdForChangeReceipt\" style=\"float: left;\">"
        //}else{
        dropDown = "<select id=\"vendorIdForChangeReceipt\" onchange=\"jQuery.ajax({type:'POST',data:'data=' + this.value , url:'${contextPath}/invoiceExpense/selectBudgetRelatedReceiptInformation',success:function(data,textStatus){jQuery('#updateItemList').html(data);checkBudgetItemForReceipt(2);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"vendorIdForChangeReceipt\" style=\"float: left;\">"
        //}

//        String dropDown = "<select id=\"vendorIdForChangeReceipt\" onchange=\"jQuery.ajax({type:'POST',data:'data=' + this.value , url:'${contextCustomer}/invoiceExpense/selectBudgetRelatedReceiptInformation',success:function(data,textStatus){jQuery('#updateItemList').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"vendorIdForChangeReceipt\" style=\"float: left;\">"
        dropDown += "<option  value='" + VendorArr[0][0] + "::" + bookInvoiceId + "::" + editId + "::" + false + "' selected>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"

        if (VendorArr.size()) {
            int tem = 0
            for (int i = 0; i < VendorArr.size(); i++) {

                if (VendorArr[i][0] == selectIndex) {
                    dropDown += "<option value='" + VendorArr[i][0] + "::" + bookInvoiceId + "::" + editId + "::" + true + "' selected>" + VendorArr[i][1] + " [" + vendorPrefix + VendorArr[i][2] + "]</option>"
                } else {
                    dropDown += "<option value='" + VendorArr[i][0] + "::" + bookInvoiceId + "::" + editId + "::" + true + "' >" + VendorArr[i][1] + " [" + vendorPrefix + VendorArr[i][2] + "]</option>"
                }

            }
        }
        dropDown += "</select>"
        return dropDown
    }
    /*
   * Get multiple bankAccountInfo
   * */

    def getAccountNoDropDown(returnIndex) {
        def returnIndexArr = returnIndex

        if (returnIndexArr.size()) {
            if (returnIndexArr.size() == 1) {
                return "<g:textField name='vendorAccountNo' value='returnIndexArr[0][1]' readonly='true' />"
            } else {
                String dropDown = "<select name='" + returnIndex + "' style='height: 62px;'>"
                for (int i = 0; i < returnIndexArr.size(); i++) {
                    dropDown += "<option value='" + returnIndexArr[i][0] + "' >" + returnIndexArr[i][1] + "</option>"
                }
                dropDown += "</select>"
            }
            return dropDown
        }
    }

    def getMomentOfSendingDropDown(returnIndex, selectIndex = 0) {

        def moInvArr = new BudgetViewDatabaseService().executeQuery("SELECT id,name FROM MomentOfSendingInvoice where status=1")

        String dropDown = "<select name='" + returnIndex + "'>"
        if (moInvArr.size()) {
            for (int i = 0; i < moInvArr.size(); i++) {
                dropDown += "<option value='" + moInvArr[i][0] + "' >" + moInvArr[i][1] + "</option>"
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getVatCategoryRate(vatId) {
        def listArr = new BudgetViewDatabaseService().executeQuery("SELECT id,rate FROM VatCategory Where id=" + vatId)
        return listArr[0][1]
    }

    def getVatCategoryAllRate() {
        def listArr = new BudgetViewDatabaseService().executeQuery("SELECT id,rate FROM VatCategory Where id!=0")
        def vatArray = ""
        if (listArr.size()) {
            listArr.each { PhnIndex ->
                def tmpData = PhnIndex[0] + "@" + PhnIndex[1] + "::"
                vatArray = vatArray + tmpData
            }
        }
        return vatArray

    }

    def getVatCategoryDetails(vatId) {
        def listArr;
        if (vatId.equals('0')) {
            listArr = new BudgetViewDatabaseService().executeQuery("SELECT id,rate,categoryName FROM VatCategory Where rate= 0")
        } else {
            listArr = new BudgetViewDatabaseService().executeQuery("SELECT id,rate,categoryName FROM VatCategory Where id=" + vatId)
        }

        return listArr[0]
    }

    def getFirstVatCategoryRate() {
        def listArr = new BudgetViewDatabaseService().executeQuery("SELECT id,rate,categoryName FROM VatCategory Order By id ASC")
        return listArr[0]
    }

    def getSpacificVatCategoryRate(vatId) {
        def listArr;
        if (vatId.equals('0')) {
            listArr = new BudgetViewDatabaseService().executeQuery("SELECT id,rate,categoryName FROM VatCategory Where rate= 0")
        } else {
            listArr = new BudgetViewDatabaseService().executeQuery("SELECT id,rate,categoryName FROM VatCategory Where id=" + vatId)
        }
        //def listArr = new BudgetViewDatabaseService().executeQuery("SELECT id,rate,categoryName FROM VatCategory WHERE id=" + vatId)
        return listArr[0]
    }

    def getSpacificVatGLAccount(vatId) {
        def listArr;
        if (vatId.equals('0')) {
            listArr = new BudgetViewDatabaseService().executeQuery("SELECT id,rate,salesGlAccount,purchaseGlAccount FROM VatCategory Where rate= 0")
        } else {
            listArr = new BudgetViewDatabaseService().executeQuery("SELECT id,rate,salesGlAccount,purchaseGlAccount FROM VatCategory Where id=" + vatId)
        }
        //def listArr = new BudgetViewDatabaseService().executeQuery("SELECT id,rate,salesGlAccount,purchaseGlAccount FROM VatCategory WHERE id=" + vatId)
        return listArr[0]
    }

    def getSpacificVatGLAccountAutomatic(vatId, BusinessCompany businessCompany) {
        def listArr
        if (vatId.equals('0')) {
            listArr = new BudgetViewDatabaseService()
                    .executeQueryAutomatic("SELECT id,rate,salesGlAccount,purchaseGlAccount FROM VatCategory Where rate= 0", businessCompany)
        } else {
            listArr = new BudgetViewDatabaseService()
                    .executeQueryAutomatic("SELECT id,rate,salesGlAccount,purchaseGlAccount FROM VatCategory Where id=" + vatId, businessCompany)
        }

        return listArr[0]
    }

    def getGeneratedJournalNo() {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=4")
        def Prefix = PrefixDataArr[0][0]
        def PrefixLength = PrefixDataArr[0][1]
        ///////////////////MAX PRODUCT ID/////
        def JournalDataArr = new BudgetViewDatabaseService().executeQuery("SELECT MAX(id) FROM bv.JournalEntry")

        def newSequence
        if (JournalDataArr[0]) {
            newSequence = JournalDataArr[0] + 1
        } else {
            newSequence = 1
        }
        def JournalInvoiceNo
        if (PrefixLength == 2) {
            JournalInvoiceNo = String.format("%02d", newSequence)
        } else if (PrefixLength == 3) {
            JournalInvoiceNo = String.format("%03d", newSequence)
        } else if (PrefixLength == 4) {
            JournalInvoiceNo = String.format("%04d", newSequence)
        } else if (PrefixLength == 5) {
            JournalInvoiceNo = String.format("%05d", newSequence)
        } else if (PrefixLength == 6) {
            JournalInvoiceNo = String.format("%06d", newSequence)
        } else if (PrefixLength == 7) {
            JournalInvoiceNo = String.format("%07d", newSequence)
        } else if (PrefixLength == 8) {
            JournalInvoiceNo = String.format("%08d", newSequence)
        } else if (PrefixLength == 9) {
            JournalInvoiceNo = String.format("%09d", newSequence)
        }

        return JournalInvoiceNo
    }

    def getGeneratedQuickNo() {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=5")
        def Prefix = PrefixDataArr[0][0]
        def PrefixLength = PrefixDataArr[0][1]
        ///////////////////MAX PRODUCT ID/////
        def JournalDataArr = new BudgetViewDatabaseService().executeQuery("SELECT MAX(id) FROM bv.QuickEntry")

        def newSequence
        if (JournalDataArr[0]) {
            newSequence = JournalDataArr[0] + 1
        } else {
            newSequence = 1
        }
        def QuickInvoiceNo
        if (PrefixLength == 2) {
            QuickInvoiceNo = String.format("%02d", newSequence)
        } else if (PrefixLength == 3) {
            QuickInvoiceNo = String.format("%03d", newSequence)
        } else if (PrefixLength == 4) {
            QuickInvoiceNo = String.format("%04d", newSequence)
        } else if (PrefixLength == 5) {
            QuickInvoiceNo = String.format("%05d", newSequence)
        } else if (PrefixLength == 6) {
            QuickInvoiceNo = String.format("%06d", newSequence)
        } else if (PrefixLength == 7) {
            QuickInvoiceNo = String.format("%07d", newSequence)
        } else if (PrefixLength == 8) {
            QuickInvoiceNo = String.format("%08d", newSequence)
        } else if (PrefixLength == 9) {
            QuickInvoiceNo = String.format("%09d", newSequence)
        }

        return QuickInvoiceNo
    }

    def getGeneratedReceiptEntryNo() {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=12")
        def Prefix = PrefixDataArr[0][0]
        def PrefixLength = PrefixDataArr[0][1]
        ///////////////////MAX PRODUCT ID/////
        def JournalDataArr = new BudgetViewDatabaseService().executeQuery("SELECT MAX(id) FROM bv.ReceiptEntry")

        def newSequence
        if (JournalDataArr[0]) {
            newSequence = JournalDataArr[0] + 1
        } else {
            newSequence = 1
        }
        def ReceiptInvoiceNo
        if (PrefixLength == 2) {
            ReceiptInvoiceNo = String.format("%02d", newSequence)
        } else if (PrefixLength == 3) {
            ReceiptInvoiceNo = String.format("%03d", newSequence)
        } else if (PrefixLength == 4) {
            ReceiptInvoiceNo = String.format("%04d", newSequence)
        } else if (PrefixLength == 5) {
            ReceiptInvoiceNo = String.format("%05d", newSequence)
        } else if (PrefixLength == 6) {
            ReceiptInvoiceNo = String.format("%06d", newSequence)
        } else if (PrefixLength == 7) {
            ReceiptInvoiceNo = String.format("%07d", newSequence)
        } else if (PrefixLength == 8) {
            ReceiptInvoiceNo = String.format("%08d", newSequence)
        } else if (PrefixLength == 9) {
            ReceiptInvoiceNo = String.format("%09d", newSequence)
        }

        return ReceiptInvoiceNo
    }

    def getGeneratedInvestmentInvoice() {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=9")
        def Prefix = PrefixDataArr[0][0]
        def PrefixLength = PrefixDataArr[0][1]
        ///////////////////MAX PRODUCT ID/////
        def JournalDataArr = new BudgetViewDatabaseService().executeQuery("SELECT MAX(id) FROM bv.InvestmentInvoice")

        def newSequence
        if (JournalDataArr[0]) {
            newSequence = JournalDataArr[0] + 1
        } else {
            newSequence = 1
        }
        def QuickInvoiceNo
        if (PrefixLength == 2) {
            QuickInvoiceNo = String.format("%02d", newSequence)
        } else if (PrefixLength == 3) {
            QuickInvoiceNo = String.format("%03d", newSequence)
        } else if (PrefixLength == 4) {
            QuickInvoiceNo = String.format("%04d", newSequence)
        } else if (PrefixLength == 5) {
            QuickInvoiceNo = String.format("%05d", newSequence)
        } else if (PrefixLength == 6) {
            QuickInvoiceNo = String.format("%06d", newSequence)
        } else if (PrefixLength == 7) {
            QuickInvoiceNo = String.format("%07d", newSequence)
        } else if (PrefixLength == 8) {
            QuickInvoiceNo = String.format("%08d", newSequence)
        } else if (PrefixLength == 9) {
            QuickInvoiceNo = String.format("%09d", newSequence)
        }

        return QuickInvoiceNo
    }

    def chartType(chartCode) {
        /*
        * selectionVal: Show label name of Active/Inactive
        * GSP usage: <%= "${new CoreParamsHelper().chartType(10001)}" %>
        * */
        def cGrpID = new BudgetViewDatabaseService().executeQuery("Select chartGroupId FROM ChartMaster WHERE accountCode='" + chartCode + "'")
        def cGID = cGrpID[0]
        /*def chartClassID = ChartGroup.executeQuery("Select chartClassId FROM ChartGroup WHERE id='" + cGID + "'")
        def ccID = chartClassID[0]*/

        /*     def showVal
        if (selectionVal == 1) {
            showVal = "Active";
        } else if (selectionVal == 2) {
            showVal = "Inactive";
        } else if (selectionVal == -2) {
            showVal = "Delete";
        } else {
            showVal = "";
        }*/
        return cGID
    }


    def getCompanyBankAccountByGlAccount(GlAccount) {

        ArrayList BankAccArr = new ArrayList()
        BankAccArr = new BudgetViewDatabaseService().executeQuery("Select bankAccountCode FROM CompanyBankGlRelation WHERE glChartCode='" + GlAccount + "'")
        if (BankAccArr.size()) {
            return BankAccArr[0]
        } else {
            return BankAccArr
        }
    }

    def getCompanyBankAccountByGlAccountAutomatic(GlAccount, BusinessCompany businessCompany) {

        ArrayList BankAccArr = new ArrayList()
        BankAccArr = new BudgetViewDatabaseService()
                .executeQueryAutomatic("Select bankAccountCode FROM CompanyBankGlRelation WHERE glChartCode='" + GlAccount + "'", businessCompany)
        if (BankAccArr.size()) {
            return BankAccArr[0]
        } else {
            return BankAccArr
        }
    }

    def getReconciliationCompanyBankAccountByReconciliationId(reconciliationId) {

        ArrayList BankAccArr = new ArrayList()
        BankAccArr = new BudgetViewDatabaseService().executeQuery("SELECT c.gl_chart_code,b.bank_account_code " +
                "FROM bank_statement_import_details_final AS a  LEFT JOIN company_bank_accounts AS b " +
                "ON a.trans_bank_account_no=b.bank_account_no LEFT JOIN company_bank_gl_relation AS c " +
                "ON b.bank_account_code=c.bank_account_code WHERE a.id='" + reconciliationId + "'")
        if (BankAccArr.size()) {
            return BankAccArr[0]
        } else {
            return BankAccArr
        }
    }


    def getFiscalYearRange() {

        LinkedHashMap gridResult
        String select = "MAX(EXTRACT(YEAR FROM year_end)) AS max_year,MIN(EXTRACT(YEAR FROM year_begin)) AS min_year "
        String selectIndex = "max_year,min_year "
        String from = "FiscalYear"
        String where = ""
        String orderBy = ""
        gridResult = new BudgetViewDatabaseService().select(select, from, where, orderBy, '', 'false', selectIndex)

        def FiscalYearArr = gridResult['dataGridList']

        //def FiscalYearArr = FiscalYear.executeQuery("SELECT MAX(EXTRACT(YEAR FROM year_end)) AS max_year,MIN(EXTRACT(YEAR FROM year_begin)) AS min_year FROM FiscalYear ")

        return [maxYear: FiscalYearArr[0][0], minYear: FiscalYearArr[0][1]]
    }

    def showGeneratedInvoiceExpense() {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=7")
        def Prefix = PrefixDataArr[0][0]
        return Prefix
    }

    def getActiveFiscalYear() {

        def FiscalYearArr = new BudgetViewDatabaseService().executeQueryAtSingle("SELECT id FROM bv.FiscalYear where status=1")

        Integer FiscalYear = 0
        if (FiscalYearArr.size()) {
            FiscalYear = FiscalYearArr[0]
        }
        return FiscalYear
    }

    def getActiveFiscalYearAutomatic(BusinessCompany businessCompany) {

        def FiscalYearArr = new BudgetViewDatabaseService().executeQueryAtSingleAutomatic("SELECT id FROM bv.FiscalYear where status=1", businessCompany)

        Integer FiscalYear = 0
        if (FiscalYearArr.size()) {
            FiscalYear = FiscalYearArr[0]
        }
        return FiscalYear
    }

    def getActiveFiscalYearString() {

        def fiscalYearArr = new BudgetViewDatabaseService().executeQueryAtSingle("SELECT DATE_FORMAT(year_begin,'%Y') As bookingYear " +
                "FROM fiscal_year WHERE status = 1")

        String fiscalYear = ""
        if (fiscalYearArr.size()) {
            fiscalYear = fiscalYearArr[0]
        }

        return fiscalYear
    }

    def Map getFiscalYearInformation() {

        def fiscalId = getActiveFiscalYear()

        Map activeFiscalYear = ["ST_DT_MM_YY"   : '', "ED_DT_MM_YY": '',
                                "ST_DT_mm"      : '', "ED_DT_mm": '',
                                "ST_DT_YY"      : '', "ED_DT_YY": '',
                                "ST_DT_yy_mm_dd": '', "ED_DT_yy_mm_dd": ''];

        LinkedHashMap gridResult
        String select = "id,DATE_FORMAT(yearBegin,'%M-%Y') As year_begin_month_year ,DATE_FORMAT(yearEnd,'%M-%Y')As year_end_month_year," +
                "DATE_FORMAT(yearBegin,'%m') AS year_begin_month,DATE_FORMAT(yearBegin,'%Y') As year_begin_year," +
                "DATE_FORMAT(yearEnd,'%m') AS year_end_month,DATE_FORMAT(yearEnd,'%Y') AS year_end_year," +
                "DATE_FORMAT(yearBegin,'%y%m%d') AS year_begin_date,DATE_FORMAT(yearEnd,'%y%m%d')  AS year_end_date," +
                "DATE_FORMAT(yearBegin,'%Y-%m-%d') AS beginDateTime,DATE_FORMAT(yearEnd,'%Y-%m-%d')  AS endDateTime"
        String selectIndex = "id,year_begin_month_year,year_end_month_year,year_begin_month,year_begin_year,year_end_month,year_end_year,year_begin_date,year_end_date,beginDateTime,endDateTime"
        String from = "FiscalYear"
        String where = " status=1 AND id='" + fiscalId + "'"
        String orderBy = "id ASC"
        gridResult = new BudgetViewDatabaseService().select(select, from, where, orderBy, '', 'true', selectIndex)

        def fiscalYearArr = gridResult['dataGridList']
        activeFiscalYear.ST_DT_MM_YY = fiscalYearArr[0][1];
        activeFiscalYear.ED_DT_MM_YY = fiscalYearArr[0][2];

        activeFiscalYear.ST_DT_mm = fiscalYearArr[0][3];
        activeFiscalYear.ED_DT_mm = fiscalYearArr[0][5];

        activeFiscalYear.ST_DT_YY = fiscalYearArr[0][4];
        activeFiscalYear.ED_DT_YY = fiscalYearArr[0][6];

        activeFiscalYear.ST_DT_yy_mm_dd = fiscalYearArr[0][9];
        activeFiscalYear.ED_DT_yy_mm_dd = fiscalYearArr[0][10];

        return activeFiscalYear;
    }

    def getActiveFiscalYearInformation(FiscalId) {

        if (FiscalId <= 0) return [];

        LinkedHashMap gridResult
        String select = "id,DATE_FORMAT(yearBegin,'%M-%Y') As year_begin_month_year ,DATE_FORMAT(yearEnd,'%M-%Y')As year_end_month_year," +
                "DATE_FORMAT(yearBegin,'%m') AS year_begin_month,DATE_FORMAT(yearBegin,'%Y') As year_begin_year," +
                "DATE_FORMAT(yearEnd,'%m') AS year_end_month,DATE_FORMAT(yearEnd,'%Y') AS year_end_year," +
                "DATE_FORMAT(yearBegin,'%y%m%d') AS year_begin_date,DATE_FORMAT(yearEnd,'%y%m%d')  AS year_end_date"
        String selectIndex = "id,year_begin_month_year,year_end_month_year,year_begin_month,year_begin_year,year_end_month,year_end_year,year_begin_date,year_end_date"
        String from = "FiscalYear"
        String where = " status=1 AND id='" + FiscalId + "'"
        String orderBy = "id ASC"
        gridResult = new BudgetViewDatabaseService().select(select, from, where, orderBy, '', 'true', selectIndex)

        def FiscalYearArr = gridResult['dataGridList']

        def request = RequestContextHolder.currentRequestAttributes().request
        def curLocale = RequestContextUtils.getLocale(request)

        SimpleDateFormat simDateFormat
        if (curLocale.getLanguage().equals(new Locale("du", "", "").getLanguage())) {

            simDateFormat = new SimpleDateFormat("MMMM-yyyy", new Locale("nl", "NL"));
            DateFormat dateFormat = new SimpleDateFormat("MMMM-yyyy");

            String stDateStr = FiscalYearArr[0][1];
            Date startDate = dateFormat.parse(stDateStr);

            String strStartDate = simDateFormat.format(startDate);
            FiscalYearArr[0][1] = strStartDate;

            String endDateStr = FiscalYearArr[0][2];
            Date endDate = dateFormat.parse(endDateStr);

            String strEndDate = simDateFormat.format(endDate);
            FiscalYearArr[0][2] = strEndDate;
        }

        if (FiscalYearArr.size()) {
            return FiscalYearArr
        } else {
            return []
        }
    }

    def getActiveFiscalYearInformationAutomatic(FiscalId, BusinessCompany businessCompany) {

        if (FiscalId <= 0) return []

        LinkedHashMap gridResult
        String select = "id,DATE_FORMAT(yearBegin,'%M-%Y') As year_begin_month_year ,DATE_FORMAT(yearEnd,'%M-%Y')As year_end_month_year," +
                "DATE_FORMAT(yearBegin,'%m') AS year_begin_month,DATE_FORMAT(yearBegin,'%Y') As year_begin_year," +
                "DATE_FORMAT(yearEnd,'%m') AS year_end_month,DATE_FORMAT(yearEnd,'%Y') AS year_end_year," +
                "DATE_FORMAT(yearBegin,'%y%m%d') AS year_begin_date,DATE_FORMAT(yearEnd,'%y%m%d')  AS year_end_date"
        String selectIndex = "id,year_begin_month_year,year_end_month_year,year_begin_month,year_begin_year," +
                        "year_end_month," + "year_end_year,year_begin_date,year_end_date"

        String from = "FiscalYear"
        String where = " status=1 AND id='" + FiscalId + "'"
        String orderBy = "id ASC"
        gridResult = new BudgetViewDatabaseService().selectAutomatic(select, from, where, orderBy, '', 'true',
                                                                                            selectIndex, 0, 0, businessCompany)

        def FiscalYearArr = gridResult['dataGridList']

        def request = RequestContextHolder.currentRequestAttributes().request
        def curLocale = RequestContextUtils.getLocale(request)

        SimpleDateFormat simDateFormat
        if (curLocale.getLanguage().equals(new Locale("du", "", "").getLanguage())) {

            simDateFormat = new SimpleDateFormat("MMMM-yyyy", new Locale("nl", "NL"))
            DateFormat dateFormat = new SimpleDateFormat("MMMM-yyyy");

            String stDateStr = FiscalYearArr[0][1]
            Date startDate = dateFormat.parse(stDateStr)

            String strStartDate = simDateFormat.format(startDate)
            FiscalYearArr[0][1] = strStartDate

            String endDateStr = FiscalYearArr[0][2]
            Date endDate = dateFormat.parse(endDateStr)

            String strEndDate = simDateFormat.format(endDate)
            FiscalYearArr[0][2] = strEndDate
        }

        if (FiscalYearArr.size()) {
            return FiscalYearArr
        } else {
            return []
        }
    }

    def importFiles() {

        def file = new File('D:\\grails\\bv\\bv\\web-app\\uploaddoc\\MT940.STA')
        def lines

        file.eachLine { line ->
            // return s.matches("[^0-9]*[12]?[0-9]{1,2}[^0-9]*");
            if (line.matches("^:61:d{6}(d{4})[C|D]")) {
                lines = lines + line
            }

            //lines=lines+ "<----->"+line
        }
        return lines

    }

    def getInvestmentVendorDropDown(returnIndex, selectIndex = '0') {

        def vendorArr = new BudgetViewDatabaseService().executeQuery("SELECT vm.id,vm.vendorName,vm.vendorCode FROM InvoiceExpense AS budget_exp, VendorMaster As vm  where budget_exp.status=1 AND budget_exp.isReverse=0 AND budget_exp.vendorId=vm.id GROUP BY budget_exp.vendorId")

        String dropDown = "<select id=\"vendorId\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'/bv/InvestmentInvoice/selectVendorRelatedInformation',success:function(data,textStatus){jQuery('#searchresults').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"vendorId\">"

        def vendorPrefix = showGeneratedVendorCode();

        if (vendorArr.size()) {
            for (int i = 0; i < vendorArr.size(); i++) {
                if (vendorArr[i][0] == Integer.parseInt(selectIndex)) {
                    dropDown += "<option value='" + vendorArr[i][0] + "' selected='selected'>" + vendorArr[i][1] + " [" + vendorPrefix + vendorArr[i][2] + "]" + "</option>"
                } else {
                    dropDown += "<option value='" + vendorArr[i][0] + "' >" + vendorArr[i][1] + " [" + vendorPrefix + vendorArr[i][2] + "]" + "</option>"
                }
            }
        }
        dropDown += "</select>"

        return dropDown
    }

    def getInvestmentBudgetDropDown(returnIndex, vendorId, selectIndex = '0') {

        def budgetItemArr = new BudgetViewDatabaseService().executeQuery("SELECT DISTINCT vm.id,vm.budgetId,vm.bookingPeriodStartMonth,vm.bookingPeriodStartYear,vm.bookingPeriodEndMonth,vm.bookingPeriodStartYear FROM InvoiceExpense AS budget_exp, BudgetItemExpense As vm  WHERE vm.status=1 AND budget_exp.isReverse=0 AND budget_exp.budgetItemExpenseId=vm.id AND budget_exp.vendorId=" + vendorId)

        String dropDown = "<select id=\"budgetItemId\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'/bv/InvestmentInvoice/selectBudgetRelatedInformation',success:function(data,textStatus){jQuery('#searchbudgetresults').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"budgetItemId\">"

        def budgetItemExpPrefix = showGeneratedBudgetExpanseCode();

        if (budgetItemArr.size()) {
            for (int i = 0; i < budgetItemArr.size(); i++) {
                if (budgetItemArr[i][0] == Integer.parseInt(selectIndex)) {
                    dropDown += "<option value='" + budgetItemArr[i][0] + "' selected='selected'>" + budgetItemArr[i][2] + "-" + budgetItemArr[i][3] + "::" + budgetItemArr[i][4] + "-" + budgetItemArr[i][5] + " [" + budgetItemExpPrefix + budgetItemArr[i][1] + "]" + "</option>"
                } else {
                    dropDown += "<option value='" + budgetItemArr[i][0] + "' >" + budgetItemArr[i][2] + "-" + budgetItemArr[i][3] + "::" + budgetItemArr[i][4] + "-" + budgetItemArr[i][5] + " [" + budgetItemExpPrefix + budgetItemArr[i][1] + "]" + "</option>"
                }
            }
        }
        dropDown += "</select>"

        return dropDown
    }


    def getInvestmentInvoiceDropDown(returnIndex, BudgetItemId, selectIndex = '0') {
        def InvestmentInvoiceArr = new BudgetViewDatabaseService().executeQuery("SELECT id,invoiceNo FROM InvoiceExpense WHERE status=1 AND isReverse=0 AND budgetItemExpenseId=" + BudgetItemId)
        String dropDown = "<select name='" + returnIndex + "'>"
        def invoiceExpPrefix = showGeneratedInvoiceExpense();

        if (InvestmentInvoiceArr.size()) {
            for (int i = 0; i < InvestmentInvoiceArr.size(); i++) {

                if (InvestmentInvoiceArr[i][0] == selectIndex) {
                    dropDown += "<option value='" + InvestmentInvoiceArr[i][0] + "' selected='selected'>" + invoiceExpPrefix + InvestmentInvoiceArr[i][1] + "</option>"
                } else {
                    dropDown += "<option value='" + InvestmentInvoiceArr[i][0] + "' >" + invoiceExpPrefix + InvestmentInvoiceArr[i][1] + "</option>"
                }
            }

        }
        dropDown += "</select>"

        return dropDown
    }


    def getVendorBankAccountDropDown(returnIndex, vendorId, selectIndex = '0') {
        def VendorBankAccountArr = new BudgetViewDatabaseService().executeQuery("SELECT id,bankAccountName,bankAccountNo FROM VendorBankAccount WHERE status=1 AND vendorId=" + vendorId)

        String dropDown = "<select name='" + returnIndex + "'>"

        if (VendorBankAccountArr.size()) {
            for (int i = 0; i < VendorBankAccountArr.size(); i++) {

                if (VendorBankAccountArr[i][0] == selectIndex) {
                    dropDown += "<option value='" + VendorBankAccountArr[i][0] + "' selected='selected'>" + VendorBankAccountArr[i][2] + "[" + VendorBankAccountArr[i][1] + "] </option>"
                } else {
                    dropDown += "<option value='" + VendorBankAccountArr[i][0] + "' >" + VendorBankAccountArr[i][2] + "[" + VendorBankAccountArr[i][1] + "] </option>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getVendorBankAccountWithoutBankDropDown(returnIndex, vendorId, selectIndex = '0') {

        def VendorBankAccountArr = new BudgetViewDatabaseService().executeQuery("SELECT id,bankAccountName,bankAccountNo FROM VendorBankAccount WHERE status=1 AND vendorId='" + vendorId + "'")

        String dropDown = "<select name='" + returnIndex + "'>"

        if (VendorBankAccountArr.size()) {
            for (int i = 0; i < VendorBankAccountArr.size(); i++) {

                if (VendorBankAccountArr[i][2] == selectIndex) {
                    dropDown += "<option value='" + VendorBankAccountArr[i][2] + "' selected='selected'>" + VendorBankAccountArr[i][2] + " </option>"
                } else {
                    dropDown += "<option value='" + VendorBankAccountArr[i][2] + "' >" + VendorBankAccountArr[i][2] + " </option>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getCustomerBankAccountWithoutBankDropDown(returnIndex, vendorId, selectIndex = '0') {
        def VendorBankAccountArr = new BudgetViewDatabaseService().executeQuery("SELECT id,bankAccountName,bankAccountNo FROM CustomerBankAccount WHERE status=1 AND customerId='" + vendorId + "'")

        String dropDown = "<select name='" + returnIndex + "'>"

        if (VendorBankAccountArr.size()) {
            for (int i = 0; i < VendorBankAccountArr.size(); i++) {

                if (VendorBankAccountArr[i][0] == selectIndex) {
                    dropDown += "<option value='" + VendorBankAccountArr[i][2] + "' selected='selected'>" + VendorBankAccountArr[i][2] + " </option>"
                } else {
                    dropDown += "<option value='" + VendorBankAccountArr[i][2] + "' >" + VendorBankAccountArr[i][2] + " </option>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }


    def getVendorMasterDropDown(returnIndex, selectValName) {
        def listArr = VendorMaster.findAll("from VendorMaster where status=1")

        String dropDown = "<select name='" + returnIndex + "'>"

        def vendorPrefix = showGeneratedVendorCode();

        if (listArr.size()) {
            for (int i = 0; i < listArr.size(); i++) {
                if (listArr[i].id == selectValName) {
                    dropDown += "<option value='" + listArr[i].id + "' selected='selected'>" + listArr[i].vendorName + " [" + vendorPrefix + listArr[i].vendorCode + "]" + "</option>"
                } else {
                    dropDown += "<option value='" + listArr[i].id + "'>" + listArr[i].vendorName + " [" + vendorPrefix + listArr[i].vendorCode + "]" + "</option>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getShopDropDown(returnIndex, selectIndex = '0') {
        //def listArr = VendorMaster.findAll("from VendorMaster where status=1 AND vendor_type='sn'")
        def listArr = new BudgetViewDatabaseService().executeQuery("Select id,vendor_code,vendor_name from VendorMaster where status=1 AND vendor_type='sn' order by vendor_name")
        def vendorPrefix = showGeneratedVendorCode();
        def g = new ValidationTagLib()

        String dropDown = "<select required=\"\" name='" + returnIndex + "'   id='" + returnIndex + "'>"
        dropDown += "<option value=\"\" selected>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"

        if (listArr.size()) {
            for (int i = 0; i < listArr.size(); i++) {
                if (Integer.parseInt(listArr[i][0].toString()) == Integer.parseInt(selectIndex.toString())) {
                    dropDown += "<option value='" + listArr[i][0] + "' selected='selected'>" + listArr[i][2] + " [" + vendorPrefix + listArr[i][1] + "]" + "</option>"
                } else {
                    dropDown += "<option value='" + listArr[i][0] + "'>" + listArr[i][2] + " [" + vendorPrefix + listArr[i][1] + "]" + "</option>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }


    def getQuickEntryBankAccountsGL(returnIndex, selectIndex) {

        def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT c.id,c.name FROM CompanyBankGlRelation AS a,ChartMaster AS b,ChartGroup AS c WHERE a.status=1 AND a.glChartCode=b.accountCode AND b.chartGroup=c.id GROUP BY b.chartGroup ORDER BY b.chartGroup ASC")

        String dropDown = "<select name='" + returnIndex + "'>"
        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]
                def CompanyBankAccArr
                CompanyBankAccArr = new BudgetViewDatabaseService().executeQuery("SELECT b.accountCode,b.accountName,c.id,c.name FROM CompanyBankGlRelation AS a,ChartMaster AS b,ChartGroup AS c WHERE a.status=1 AND a.glChartCode=b.accountCode AND b.chartGroup=c.id AND b.chartGroup=" + catId + " ORDER BY b.accountCode ASC")

                if (CompanyBankAccArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < CompanyBankAccArr.size(); j++) {

                        if (CompanyBankAccArr[j][0] == selectIndex) {
                            dropDown += "<option value='" + CompanyBankAccArr[j][0] + "' selected='selected'>" + CompanyBankAccArr[j][0] + " - " + CompanyBankAccArr[j][1] + "</option>"
                        } else {
                            dropDown += "<option value='" + CompanyBankAccArr[j][0] + "' >" + CompanyBankAccArr[j][0] + " - " + CompanyBankAccArr[j][1] + "</option>"
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getQuickJournalChartGroupDropDown(returnIndex, selectIndex = "") {
        def allBankGLAccount = new BudgetViewDatabaseService().executeQuery("SELECT glChartCode FROM CompanyBankGlRelation")

        def notInString = "'0'"
        for (int k = 0; k < allBankGLAccount.size(); k++) {
            notInString = notInString + ",'" + allBankGLAccount[k] + "'"
        }
        def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,name As categoryName FROM ChartGroup where status=1")

        String dropDown = "<select name='" + returnIndex + "'>"
        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]
                def ProductArr
                ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,accountCode,accountName FROM bv.ChartMaster where status='1' AND chart_group_id='" + catId + "' AND account_code NOT IN(" + notInString + ")")

                if (ProductArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < ProductArr.size(); j++) {

                        if (ProductArr[j][1] == selectIndex) {
                            dropDown += "<option value='" + ProductArr[j][1] + "' selected='selected'>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        } else {
                            dropDown += "<option value='" + ProductArr[j][1] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getQuickJournalChartGroupDropDownWithId(returnIndex, selectIndex = "") {
        //println selectIndex
        def allBankGLAccount = new BudgetViewDatabaseService().executeQuery("SELECT glChartCode FROM CompanyBankGlRelation")

        def notInString = "'0'"
        for (int k = 0; k < allBankGLAccount.size(); k++) {
            notInString = notInString + ",'" + allBankGLAccount[k] + "'"
        }
        def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,name As categoryName FROM ChartGroup where status=1")

        String dropDown = "<select id='" + returnIndex + "'>"
        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]
                def ProductArr
                ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,accountCode,accountName FROM bv.ChartMaster where status='1' AND chart_group_id='" + catId + "' AND account_code NOT IN(" + notInString + ")")

                if (ProductArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < ProductArr.size(); j++) {
                        if (ProductArr[j][1] == selectIndex.toString()) {
                            dropDown += "<option value='" + ProductArr[j][1] + "' selected='selected'>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        } else {
                            dropDown += "<option value='" + ProductArr[j][1] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }


    def getInternalBankingChartGroupDropDown(returnIndex, selectIndex = "") {

        def allBankGLAccount = new BudgetViewDatabaseService().executeQuery("SELECT glChartCode FROM CompanyBankGlRelation")

        def notInString = "'0'"
        for (int k = 0; k < allBankGLAccount.size(); k++) {
            notInString = notInString + ",'" + allBankGLAccount[k] + "'"
        }
        def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,name As categoryName FROM ChartGroup where status=1")

        String dropDown = "<select name='" + returnIndex + "'>"
        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]
                def ProductArr
                ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,accountCode,accountName FROM bv.ChartMaster where status='1' AND chart_group_id='" + catId + "' AND account_code IN(" + notInString + ")")

                if (ProductArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < ProductArr.size(); j++) {

                        if (ProductArr[j][1] == selectIndex) {
                            dropDown += "<option value='" + ProductArr[j][1] + "' selected='selected'>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        } else {
                            dropDown += "<option value='" + ProductArr[j][1] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }


    def getInvestmentChartGroupDropDown(returnIndex, selectIndex = 0) {
        def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,name As categoryName FROM ChartGroup where status=1")
        String dropDown = "<select name='" + returnIndex + "'>"
        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]
                def ProductArr
                ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,accountCode,accountName FROM bv.ChartMaster where status='1' AND chart_group_id='" + catId + "'")
                if (ProductArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < ProductArr.size(); j++) {

                        if (ProductArr[j][1] == selectIndex) {
                            dropDown += "<option value='" + ProductArr[j][1] + "' selected='selected'>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        } else {
                            dropDown += "<option value='" + ProductArr[j][1] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def customerInvoiceIncome(customerId) {

        //def invoices = InvoiceIncome.executeQuery("Select II.customerId as customerId, II.customerAccountNo as customerAccountNo, II.invoiceNo as invoiceNo, II.transDate as transDate, II.totalGlAmount as totalGlAmount FROM InvoiceIncome as II where II.customerId='" + customerId + "' order by II.customerId")
        //def invoices = InvoiceIncome.executeQuery('SELECT II.customerId AS customerId,CONCAT((CASE II.bookingPeriod WHEN "1" THEN "Jan" WHEN "2" THEN "Feb" WHEN "3" THEN "Mar" WHEN "4" THEN "Apr" WHEN "5" THEN "May" WHEN "6" THEN "Jun" WHEN "7" THEN "Jul" WHEN "8" THEN "Aug" WHEN "9" THEN "Sep" WHEN "10" THEN "Oct" WHEN "11" THEN "Nov" WHEN "12" THEN "Dec" END),"-",II.bookingYear) AS bookPeriod, CONCAT(SI.prefix,"-",II.invoiceNo),CONCAT(SP.prefix,"-",BI.budgetId),II.customerAccountNo,DATE_FORMAT(II.transDate,"%d-%M-%Y"),DATE_FORMAT(II.dueDate,"%d-%M-%Y"),II.totalGlAmount+II.totalVat,II.paidStatus,II.paidAmount FROM InvoiceIncome AS II,BudgetItemIncome AS BI,SystemPrefix AS SP,SystemPrefix AS SI WHERE  BI.id=II.budgetItemIncomeId AND II.customerId!=0 AND II.paidStatus!=1 AND II.customerId=15 AND SP.id=11 AND SI.id=8 AND isReverse=0 ORDER BY II.id ASC')
        def invoices = new BudgetViewDatabaseService().executeQuery("SELECT II.customerId AS customerId," +
                "CONCAT((CASE II.bookingPeriod WHEN '1' THEN 'Jan' WHEN '2' THEN 'Feb' WHEN '3' THEN 'Mar' WHEN '4' THEN 'Apr' " +
                "WHEN '5' THEN 'May' WHEN '6' THEN 'Jun' WHEN '7' THEN 'Jul' WHEN '8' THEN 'Aug' WHEN '9' THEN 'Sep' WHEN '10' " +
                "THEN 'Oct' WHEN '11' THEN 'Nov' WHEN '12' THEN 'Dec' END),'-',II.bookingYear) AS bookPeriod, CONCAT(SI.prefix,'-',II.invoiceNo)," +
                "CONCAT(SP.prefix,'-',BI.budgetId),II.customerAccountNo,DATE_FORMAT(II.transDate,'%d-%m-%Y'),DATE_FORMAT(II.dueDate,'%d-%m-%Y')," +
                "II.totalGlAmount+II.totalVat,II.paidStatus,II.paidAmount,II.id FROM InvoiceIncome AS II,BudgetItemIncome AS BI,SystemPrefix AS SP," +
                "SystemPrefix AS SI WHERE  BI.id=II.budgetItemIncomeId AND II.customerId!=0 AND II.paidStatus!=1 AND II.customerId=" + customerId +
                " AND SP.id=11 AND SI.id=8 AND II.isReverse=0 ORDER BY II.id ASC")

        return invoices
    }

    def customerInvoiceExpense(vendorId) {

        //def invoices = InvoiceIncome.executeQuery("Select II.customerId as customerId, II.customerAccountNo as customerAccountNo, II.invoiceNo as invoiceNo, II.transDate as transDate, II.totalGlAmount as totalGlAmount FROM InvoiceIncome as II where II.customerId='" + customerId + "' order by II.customerId")
        //def invoices = InvoiceIncome.executeQuery('SELECT II.customerId AS customerId,CONCAT((CASE II.bookingPeriod WHEN "1" THEN "Jan" WHEN "2" THEN "Feb" WHEN "3" THEN "Mar" WHEN "4" THEN "Apr" WHEN "5" THEN "May" WHEN "6" THEN "Jun" WHEN "7" THEN "Jul" WHEN "8" THEN "Aug" WHEN "9" THEN "Sep" WHEN "10" THEN "Oct" WHEN "11" THEN "Nov" WHEN "12" THEN "Dec" END),"-",II.bookingYear) AS bookPeriod, CONCAT(SI.prefix,"-",II.invoiceNo),CONCAT(SP.prefix,"-",BI.budgetId),II.customerAccountNo,DATE_FORMAT(II.transDate,"%d-%M-%Y"),DATE_FORMAT(II.dueDate,"%d-%M-%Y"),II.totalGlAmount+II.totalVat,II.paidStatus,II.paidAmount FROM InvoiceIncome AS II,BudgetItemIncome AS BI,SystemPrefix AS SP,SystemPrefix AS SI WHERE  BI.id=II.budgetItemIncomeId AND II.customerId!=0 AND II.paidStatus!=1 AND II.customerId=15 AND SP.id=11 AND SI.id=8 AND isReverse=0 ORDER BY II.id ASC')
        def invoices = new BudgetViewDatabaseService().executeQuery("SELECT II.vendorId AS vendorId," +
                "CONCAT((CASE II.bookingPeriod WHEN '1' THEN 'Jan' WHEN '2' THEN 'Feb' WHEN '3' THEN 'Mar' WHEN '4' THEN 'Apr' WHEN '5' " +
                "THEN 'May' WHEN '6' THEN 'Jun' WHEN '7' THEN 'Jul' WHEN '8' THEN 'Aug' WHEN '9' THEN 'Sep' WHEN '10' THEN 'Oct' " +
                "WHEN '11' THEN 'Nov' WHEN '12' THEN 'Dec' END),'-',II.bookingYear) AS bookPeriod, CONCAT(SI.prefix,'-',II.invoiceNo)," +
                "CONCAT(SP.prefix,'-',BI.budgetId),II.vendorAccountNo,DATE_FORMAT(II.transDate,'%d-%m-%Y'),DATE_FORMAT(II.dueDate,'%d-%m-%Y')," +
                "II.totalGlAmount+II.totalVat,II.paidStatus,II.paidAmount,II.id FROM InvoiceExpense AS II,BudgetItemExpense AS BI," +
                "SystemPrefix AS SP,SystemPrefix AS SI WHERE  BI.id=II.budgetItemExpenseId  AND II.vendorId!=0 AND II.paidStatus!=1 AND " +
                "II.vendorId=" + vendorId + " AND SP.id=6 AND SI.id=7 AND II.isReverse=0 ORDER BY II.id ASC")

        return invoices
    }


    def makeDropDownBankAccountType(returnIndex, selectIndex) {

        def g = new ValidationTagLib()
        def VatCatArr = new BudgetViewDatabaseService().executeQuery("SELECT name FROM BankAccountType WHERE status=1")
        String dropDown = "<select name='" + returnIndex + "'  id='" + returnIndex + "'>"
        if (VatCatArr.size()) {
            for (int i = 0; i < VatCatArr.size(); i++) {
                if (VatCatArr[i][0] == selectIndex) {
                    dropDown += "<option value='" + VatCatArr[i][0] + "' selected='selected'>" + VatCatArr[i][0] + "</option>"
                } else {
                    dropDown += "<option value='" + VatCatArr[i][0] + "'>" + VatCatArr[i][0] + "</option>"
                }
            }
        } else {
            dropDown += "<option>" + g.message(code: 'coreParamsHelper.noBankAccountTypeSetup.label') + "</option>"
        }

        dropDown += "</select>"
        return dropDown
    }

    def makeDropDownBankAccountCategory(returnIndex, selectIndex, contextPath) {

        ArrayList categoryArr = new ArrayList()
        def g = new ValidationTagLib()
        categoryArr = [
                ['cba', g.message(code: 'companyBankAccount.bankAccCat.companyAccount.label')],
                ['pba', g.message(code: 'companyBankAccount.bankAccCat.privateAccount.label')]
        ]

        String dropDown = "<select id=\"${returnIndex}\"  onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value,  url:'${contextPath}/CompanyBankAccounts/glAccountRelatedSelection',success:function(data,textStatus){jQuery('#ajaxSubGlAccount').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"${returnIndex}\" required>"

        dropDown += "<option value=\"\" selected>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
        for (int i = 0; i < categoryArr.size(); i++) {
            if (categoryArr[i][0].toString() == selectIndex) {
                dropDown += "<option value='" + categoryArr[i][0] + "' selected='selected'>" + categoryArr[i][1] + "</option>"
            } else {
                dropDown += "<option value='" + categoryArr[i][0] + "'>" + categoryArr[i][1] + "</option>"
            }
        }

        dropDown += "</select>"
        return dropDown

    }

    def getPdfInvoiceProcessDropdown(returnIndex, selectIndex) {

        ArrayList categoryArr = new ArrayList()
        def g = new ValidationTagLib()
        categoryArr = [
                [1, 'True'],
                [0, 'False']
        ]

        String dropDown = "<select id=\"${returnIndex}\"  name=\"${returnIndex}\" required>"

        dropDown += "<option value=\"\" selected>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
        for (int i = 0; i < categoryArr.size(); i++) {
            if (categoryArr[i][0].toString() == selectIndex) {
                dropDown += "<option value='" + categoryArr[i][0] + "' selected='selected'>" + categoryArr[i][1] + "</option>"
            } else {
                dropDown += "<option value='" + categoryArr[i][0] + "'>" + categoryArr[i][1] + "</option>"
            }
        }

        dropDown += "</select>"
        return dropDown

    }

    def getDirectDebitDropdown(returnIndex, selectIndex) {

        ArrayList categoryArr = new ArrayList()
        def g = new ValidationTagLib()
        categoryArr = [
                [1, 'True'],
                [0, 'False']
        ]

        String dropDown = "<select id=\"${returnIndex}\"  name=\"${returnIndex}\" required>"

        dropDown += "<option value=\"\" selected>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
        for (int i = 0; i < categoryArr.size(); i++) {
            if (categoryArr[i][0].toString() == selectIndex) {
                dropDown += "<option value='" + categoryArr[i][0] + "' selected='selected'>" + categoryArr[i][1] + "</option>"
            } else {
                dropDown += "<option value='" + categoryArr[i][0] + "'>" + categoryArr[i][1] + "</option>"
            }
        }

        dropDown += "</select>"
        return dropDown

    }

    def getZorgSectorDropdown(returnIndex, selectIndex) {

        ArrayList categoryArr = new ArrayList()
        def g = new ValidationTagLib()
        categoryArr = [
                [1, 'True'],
                [0, 'False']
        ]

        String dropDown = "<select id=\"${returnIndex}\"  name=\"${returnIndex}\" required>"

        dropDown += "<option value=\"\" selected>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
        for (int i = 0; i < categoryArr.size(); i++) {
            if (categoryArr[i][0].toString() == selectIndex) {
                dropDown += "<option value='" + categoryArr[i][0] + "' selected='selected'>" + categoryArr[i][1] + "</option>"
            } else {
                dropDown += "<option value='" + categoryArr[i][0] + "'>" + categoryArr[i][1] + "</option>"
            }
        }

        dropDown += "</select>"
        return dropDown

    }

    def getBookingPeriodDetails(id, VarName) {
        //println('id='+id)
        //println('VarName='+VarName)
        //String BookingInstance = CoreParams.findByVarName(VarName)
        def DataInstanceArr = new BudgetViewDatabaseService().executeQuery("SELECT paramsName FROM CoreParams where var_name='${VarName}'")
        String BookingInstance = DataInstanceArr[0][0]

        //println('BookingInstance=='+BookingInstance)

        def firstArr = BookingInstance.split("::")
        //println('firstArr'+firstArr)
        String strValue
        String valArr
        Integer indexPos
        String strIndex
        Integer varLength
        String displayValue
        firstArr.each { phn ->
            indexPos = phn.lastIndexOf('{');
            strIndex = phn.substring(0, indexPos)
            varLength = phn.length();
            strValue = phn.substring(indexPos + 2, varLength - 2)
            //println('strValue===='+strValue)
            if (strValue == String.valueOf(id)) {
                displayValue = strIndex
            }
        }
        return displayValue
    }

    def showSystemPrefix(id) {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=" + id)
        //def PrefixDataArr = SystemPrefix.executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=" + id)
        def Prefix = PrefixDataArr[0][0]
        return Prefix
    }

    def getVendorName(id) {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT vendorName FROM bv.VendorMaster where id=" + id)
        //println("PrefixDataArr: "+PrefixDataArr)
        def venName = PrefixDataArr[0][0]
        return venName
    }

    /*
    * Show vendor name and code
    *
    * */

    def showVendorName(id) {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT vendorCode,vendorName FROM bv.VendorMaster where id=" + id)
        def vendorPrefix = showGeneratedVendorCode()

        def venCode = ' [' + vendorPrefix + "-" + PrefixDataArr[0][0] + ']'

        Integer maxLength = 26
        Integer remainLength = maxLength - venCode.length()
        def venName = PrefixDataArr[0][1]
        if (venName.length() > remainLength) {
            venName = venName.substring(0, remainLength)
        }

        def Prefix = venName + venCode

        return Prefix
    }

    /*******
     * Show Customer Name and code
     ********* */
    def showCustomerName(id) {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT customerCode,customerName FROM bv.CustomerMaster where id=" + id)
        def customerPrefix = showGeneratedCustomerCode()

        def cusCode = ' [' + customerPrefix + "-" + PrefixDataArr[0][0] + ']'

        Integer maxLength = 26
        Integer remainLength = maxLength - cusCode.length()
        def cusName = PrefixDataArr[0][1]
        if (cusName.length() > remainLength) {
            cusName = cusName.substring(0, remainLength)
        }
        def Prefix = cusName + cusCode
        return Prefix
    }

    /*******
     * Show Only Customer Name
     ********* */
    def getCustomerName(id) {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT customerName FROM bv.CustomerMaster where id=" + id)

        def cusName = PrefixDataArr[0][0]
        return cusName
    }

    def getIbanPrefixFromAccountNo(def bankAccountNo) {
        String strQuery = """SELECT iba_number FROM bank_statement_import_details_final where by_bank_account_no='${
            bankAccountNo
        }'"""
        def dataArr = new BudgetViewDatabaseService().executeQueryWithoutCamelCase(strQuery)

        def ibanPrefix = ""
        if (dataArr[0].size() > 0) {
            ibanPrefix = dataArr[0][0]
        }

        return ibanPrefix
    }


    def doIncomeReconcilation(id, accountNo, amount, paidAmount) {

        def BankStatementImportDetailsArr = new BudgetViewDatabaseService().executeQuery("SELECT id,SUM(amount),SUM(remainAmount) FROM bv.BankStatementImportDetails where byBankAccountNo='" + accountNo.replace(".", "") + "' AND debitCredit='C' GROUP BY byBankAccountNo")
        def returnAmount = 0


        if (BankStatementImportDetailsArr.size()) {

            def tempPaidAmount = amount - paidAmount
            if (tempPaidAmount > 0) {
                def tempRemainAmount = BankStatementImportDetailsArr[0][2]
                if (tempRemainAmount > 0) {
                    if (tempPaidAmount <= tempRemainAmount) {
                        def BankStatementImportDetailsUpdate = BankStatementImportDetails.executeUpdate("UPDATE bv.BankStatementImportDetails SET remainAmount=remainAmount-" + tempPaidAmount + " WHERE id=" + BankStatementImportDetailsArr[0][0])
                        returnAmount = tempPaidAmount
                        // def InvoiceIncomeUpdate = InvoiceIncome.executeUpdate("UPDATE bv.InvoiceIncome SET paidAmount=paidAmount+" + returnAmount + " WHERE id=" + id)
                    } else {
                        def BankStatementImportDetailsUpdate = BankStatementImportDetails.executeUpdate("UPDATE bv.BankStatementImportDetails SET remainAmount=0 WHERE id=" + BankStatementImportDetailsArr[0][0])
                        returnAmount = tempRemainAmount
                        // def InvoiceIncomeUpdate = InvoiceIncome.executeUpdate("UPDATE bv.InvoiceIncome SET paidAmount=paidAmount+" + returnAmount + " WHERE id=" + id)
                    }
                }

            }
        }

        return returnAmount
    }

    def doExpanseReconcilation(id, accountNo, amount, paidAmount) {
        def BankStatementImportDetailsArr = new BudgetViewDatabaseService().executeQuery("SELECT id,SUM(amount),SUM(remainAmount) FROM bv.BankStatementImportDetails where byBankAccountNo='" + accountNo.replace(".", "") + "' AND debitCredit='D' GROUP BY byBankAccountNo")
        def returnAmount = 0
        if (BankStatementImportDetailsArr.size()) {

            def tempPaidAmount = amount - paidAmount
            if (tempPaidAmount > 0) {
                def tempRemainAmount = BankStatementImportDetailsArr[0][2]
                if (tempRemainAmount > 0) {
                    if (tempPaidAmount <= tempRemainAmount) {
                        def BankStatementImportDetailsUpdate = BankStatementImportDetails.executeUpdate("UPDATE bv.BankStatementImportDetails SET remainAmount=remainAmount-" + tempPaidAmount + " WHERE id=" + BankStatementImportDetailsArr[0][0])
                        returnAmount = tempPaidAmount
                        // def InvoiceIncomeUpdate = InvoiceIncome.executeUpdate("UPDATE bv.InvoiceIncome SET paidAmount=paidAmount+" + returnAmount + " WHERE id=" + id)
                    } else {
                        def BankStatementImportDetailsUpdate = BankStatementImportDetails.executeUpdate("UPDATE bv.BankStatementImportDetails SET remainAmount=0 WHERE id=" + BankStatementImportDetailsArr[0][0])
                        returnAmount = tempRemainAmount
                        // def InvoiceIncomeUpdate = InvoiceIncome.executeUpdate("UPDATE bv.InvoiceIncome SET paidAmount=paidAmount+" + returnAmount + " WHERE id=" + id)
                    }
                }

            }
        }
        return returnAmount
    }

    def getFirstVendorID() {
        def listArr = new BudgetViewDatabaseService().executeQuery("SELECT id,defaultGlAccount,vat FROM VendorMaster WHERE status=1 AND byShop!=1 AND vendorType!='sn' Order By id ASC")
        return listArr[0]
    }

    def getVendorDefaultGlAccount(vendorId) {
        LinkedHashMap gridResult
        String select = "defaultGlAccount"
        String selectIndexes = "defaultGlAccount"
        String from = "VendorMaster"
        String where = "id=" + vendorId
        String orderBy = ""
        gridResult = new BudgetViewDatabaseService().select(select, from, where, orderBy, '', 'true', selectIndexes)
        def VendorDataArr = gridResult['dataGridList'][0]
        //def VendorDataArr = new BudgetViewDatabaseService().executeQuery("SELECT defaultGlAccount FROM VendorMaster WHERE id=" + vendorId)
        return VendorDataArr[0]

    }

    def getVendorDefaultVATCategory(vendorId) {

        LinkedHashMap gridResult
        String select = "vat"
        String selectIndexes = "vat"
        String from = "VendorMaster"
        String where = "id=" + vendorId
        String orderBy = ""
        gridResult = new BudgetViewDatabaseService().select(select, from, where, orderBy, '', 'true', selectIndexes)
        def VendorDataArr = gridResult['dataGridList'][0]
        return VendorDataArr[0]

    }

    def getFirstCustomerID() {

        LinkedHashMap gridResult
        String select = "id,defaultGlAccount,vat"
        String selectIndexes = "id,defaultGlAccount,vat"
        String from = "CustomerMaster"
        String where = "status='1'"
        String orderBy = "id ASC"
        gridResult = new BudgetViewDatabaseService().select(select, from, where, orderBy, '', 'true', selectIndexes)
        def listArr = gridResult['dataGridList']
        //def listArr = CustomerMaster.executeQuery("SELECT id,defaultGlAccount,vat FROM CustomerMaster Order By id ASC")
        return listArr[0]
    }

    def getCustomerDefaultGlAccount(customerId) {

        LinkedHashMap gridResult
        String select = "defaultGlAccount"
        String selectIndexes = "defaultGlAccount"
        String from = "CustomerMaster"
        String where = "id=" + customerId
        String orderBy = ""
        gridResult = new BudgetViewDatabaseService().select(select, from, where, orderBy, '', 'true', selectIndexes)
        def VendorDataArr = gridResult['dataGridList'][0]
        //def VendorDataArr = CustomerMaster.executeQuery("SELECT defaultGlAccount FROM CustomerMaster WHERE id=" + customerId)
        return VendorDataArr[0]
    }

    def getCustomerDefaultVATCategory(customerId) {

        LinkedHashMap gridResult
        String select = "vat"
        String selectIndexes = "vat"
        String from = "CustomerMaster"
        String where = "id=" + customerId
        String orderBy = ""
        gridResult = new BudgetViewDatabaseService().select(select, from, where, orderBy, '', 'true', selectIndexes)
        def VendorDataArr = gridResult['dataGridList'][0]
        //def VendorDataArr = CustomerMaster.executeQuery("SELECT vat FROM CustomerMaster WHERE id=" + customerId)
        return VendorDataArr[0]
    }

    def getVendorExpanseInvoiceDropDown(returnIndex, selectIndex = '0', contextCustomer = "") {

        def VendorArr = new BudgetViewDatabaseService().executeQuery("SELECT id,vendorName,vendorCode FROM VendorMaster where status=1 AND byShop !=1 AND vendorType='vn' ORDER BY vendorName ASC")
        def vendorPrefix = showGeneratedVendorCode()

        def g = new ValidationTagLib()

        //String dropDown = "<select name='" + returnIndex + "' >"
        String dropDown = "<select id=\"vendorId\" required=\"\" name=\"vendorId\">"
        dropDown += "<option value=\"\" selected>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"

        if (VendorArr.size()) {
            int tem = 0
            for (int i = 0; i < VendorArr.size(); i++) {
                String venId = VendorArr[i][0].toString();
//                println("venId "+venId + " selectIndex "+selectIndex);

                if (selectIndex == venId) {
                    dropDown += "<option value='" + VendorArr[i][0] + "' selected>" + VendorArr[i][1] + " [" + vendorPrefix + VendorArr[i][2] + "]</option>"
                } else {
                    dropDown += "<option value='" + VendorArr[i][0] + "' >" + VendorArr[i][1] + " [" + vendorPrefix + VendorArr[i][2] + "]</option>"
                }

            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getQuickEntryVendorExpanseInvoiceDropDown(returnIndex, selectIndex = '0', contextCustomer = "") {

        def VendorArr = new BudgetViewDatabaseService().executeQuery("SELECT id,vendorName,vendorCode FROM VendorMaster where status=1 AND byShop !=1 AND vendorType='vn' ORDER BY vendorName ASC")
        def vendorPrefix = showGeneratedVendorCode()

        def g = new ValidationTagLib()

        //String dropDown = "<select name='" + returnIndex + "' >"
        String dropDown = "<select id=\"vendorId\" required=\"\" name=\"vendorId\" style=\"width:160px;\">"
        dropDown += "<option value=\"\" selected>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"

        if (VendorArr.size()) {
            int tem = 0
            for (int i = 0; i < VendorArr.size(); i++) {
                String venId = VendorArr[i][0].toString();
//                println("venId "+venId + " selectIndex "+selectIndex);

                if (selectIndex == venId) {
                    dropDown += "<option  value='" + VendorArr[i][0] + "' selected>" + VendorArr[i][1] + " [" + vendorPrefix + VendorArr[i][2] + "]</option>"
                } else {
                    dropDown += "<option value='" + VendorArr[i][0] + "' >" + VendorArr[i][1] + " [" + vendorPrefix + VendorArr[i][2] + "]</option>"
                }

            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getCustomerIncomeInvoiceDropDown(returnIndex, selectIndex = '0', contextCustomer = "") {

        def VendorArr = new BudgetViewDatabaseService().executeQuery("SELECT id,customerName,customerCode FROM CustomerMaster where status=1 AND customerType='cn' ORDER BY customerName ASC")
        def vendorPrefix = showGeneratedCustomerCode()

        String dropDown = "<select id=\"customerId\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value,  url:'${contextCustomer}/InvoiceIncome/customerRelatedInfoSelection',success:function(data,textStatus){jQuery('#ajaxCustomerRelatedInfoShow').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"customerId\">"

        if (VendorArr.size()) {
            int tem = 0
            for (int i = 0; i < VendorArr.size(); i++) {
                if (selectIndex == VendorArr[i][0]) {
                    dropDown += "<option value='" + VendorArr[i][0] + "' selected >" + VendorArr[i][1] + " [" + vendorPrefix + VendorArr[i][2] + "]</option>"
                } else {
                    dropDown += "<option value='" + VendorArr[i][0] + "' >" + VendorArr[i][1] + " [" + vendorPrefix + VendorArr[i][2] + "]</option>"
                }

            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getCustomerIncomeInvoiceDropDownWithSelect(returnIndex, selectIndex = '0', contextCustomer = "") {

//      String dropDown = "<select id=\"customerId\" required=\"\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/InvoiceIncome/customerRelatedInfoSelection',success:function(data,textStatus){jQuery('#ajaxCustomerRelatedInfoShow').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"customerId\">"

        def customerArr = new BudgetViewDatabaseService().executeQuery("SELECT id,customerName,customerCode FROM CustomerMaster where status=1 AND customerType='cn' ORDER BY customerName ASC")
        def customerPrefix = showGeneratedCustomerCode()
        def g = new ValidationTagLib()

        String dropDown = "<select id=\"customerId\" required=\"\" name=\"customerId\" onchange= 'getCutomerTerms(this.value);'>"
        dropDown += "<option value=\"\" selected>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"

        if (customerArr.size()) {
            int tem = 0
            for (int i = 0; i < customerArr.size(); i++) {
                String cusId = customerArr[i][0] + "";
//                println("cusId : "+cusId + " selectIndex : "+selectIndex);
                if (selectIndex == cusId) {
//                    println("selectIndex == cusId");
                    dropDown += "<option value='" + customerArr[i][0] + "' selected>" + customerArr[i][1] + " [" + customerPrefix + customerArr[i][2] + "]</option>"
                } else {
                    dropDown += "<option value='" + customerArr[i][0] + "' >" + customerArr[i][1] + " [" + customerPrefix + customerArr[i][2] + "]</option>"
                }

            }
        }

        dropDown += "</select>"
        return dropDown
    }


    def getExistCustomerForReconcilationDropDown(returnIndex, selectIndex = '0') {

        def customerArr = new BudgetViewDatabaseService().executeQuery("SELECT id,customerName,customerCode FROM CustomerMaster where status=1 AND customerType='cn' ORDER BY customerName ASC")
        //println('VendorArr=====' + VendorArr)
        def g = new ValidationTagLib()
        def customerPrefix = showGeneratedCustomerCode()
        //String dropDown = "<select name='" + returnIndex + "'>"
        String dropDown = "<select id=\"customerNameList\" onchange=\"autoFillBankName()\" name='" + returnIndex + "' required>"

        dropDown += "<option value=\"\" selected>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
        if (customerArr.size()) {
            int tem = 0
            for (int i = 0; i < customerArr.size(); i++) {

                if (selectIndex == customerArr[i][0]) {
                    dropDown += "<option value='" + customerArr[i][0] + "' selected>" + customerArr[i][1] + " [" + customerPrefix + customerArr[i][2] + "]</option>"
                } else {
                    dropDown += "<option value='" + customerArr[i][0] + "' >" + customerArr[i][1] + " [" + customerPrefix + customerArr[i][2] + "]</option>"
                }

            }
        }

        dropDown += "</select>"
        return dropDown
    }


    def getExistPrivateBudgetForReconcilationDropDown(returnIndex, selectIndex = '0') {

        def budgetArr = new BudgetViewDatabaseService().executeQuery("SELECT id, budget_name,budget_code,budget_type FROM private_budget_master where `status` = 1 ORDER BY budget_name ASC")
        //println('VendorArr=====' + VendorArr)
        def g = new ValidationTagLib()
//        def customerPrefix = showGeneratedCustomerCode()
        def budgetPrefix = showGeneratedPrivateCode()
        //String dropDown = "<select name='" + returnIndex + "'>"
        String dropDown = "<select id='" + returnIndex + "' onchange=\"autoFillBankName()\" name='" + returnIndex + "' required>"
//        String dropDown = "<select id=\"customerNameList\" onchange=\"autoFillBankName()\" name='" + returnIndex + "' required>"

        dropDown += "<option value=\"\" selected>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
        if (budgetArr.size()) {
            int tem = 0
            for (int i = 0; i < budgetArr.size(); i++) {

                def budgetType = ''
                if (budgetArr[i][3] == 1) {
                    budgetType = 'Income'
                } else if (budgetArr[i][3] == 2) {
                    budgetType = 'Fixed Expense'
                } else if (budgetArr[i][3] == 3) {
                    budgetType = 'Variable Expense'
                }

                if (selectIndex == budgetArr[i][0]) {
                    dropDown += "<option value='" + budgetArr[i][0] + "' selected>" + budgetArr[i][1] + " [" + budgetPrefix + budgetArr[i][2] + "]-" + budgetType + "</option>"
                } else {
                    dropDown += "<option value='" + budgetArr[i][0] + "' >" + budgetArr[i][1] + " [" + budgetPrefix + budgetArr[i][2] + "]-" + budgetType + "</option>"
                }

            }
        }

        dropDown += "</select>"
        return dropDown
    }


    def getExistVendorForReconcilationDropDown(returnIndex, selectIndex = '0') {

        def vendorArr = new BudgetViewDatabaseService().executeQuery("SELECT id,vendorName,vendorCode FROM VendorMaster where status=1 AND vendorType='vn' OR vendor_type='rp' ORDER BY vendorName ASC")
        def vendorPrefix = showGeneratedVendorCode()
        def g = new ValidationTagLib()
        //String dropDown = "<select onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'/bankReconciliation/reconcilateBankTrans',success:function(data,textStatus){jQuery('#ajaxBankAccountNameShow').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name='" + returnIndex + "'>"
        String dropDown = "<select id=\"vendorNameList\" onchange=\"autoFillBankName()\" name='" + returnIndex + "' required >"

        dropDown += "<option value=\"\" selected>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
        if (vendorArr.size()) {
            int tem = 0
//            dropDown += "<option value='0' >"+g.message(code:'bv.undoReconciliation.Select.label')+"</option>"
            for (int i = 0; i < vendorArr.size(); i++) {

                if (selectIndex == vendorArr[i][0]) {
                    dropDown += "<option value='" + vendorArr[i][0] + "' selected>" + vendorArr[i][1] + " [" + vendorPrefix + vendorArr[i][2] + "]</option>"
                } else {
                    dropDown += "<option value='" + vendorArr[i][0] + "' >" + vendorArr[i][1] + " [" + vendorPrefix + vendorArr[i][2] + "]</option>"
                }

            }
        }
        dropDown += "</select>"
        return dropDown
    }


    def getVendorReceiptDropDown(returnIndex, selectIndex = '0') {

        def VendorArr = new BudgetViewDatabaseService().executeQuery("SELECT id,vendorName,vendorCode FROM VendorMaster where status=1 AND vendorType='rp' ORDER BY vendorName ASC")
        def vendorPrefix = showGeneratedVendorCode()
        def g = new ValidationTagLib()

        //String dropDown = "<select name='" + returnIndex + "' >"
        String dropDown = "<select id=\"vendorId\" required= \"\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'/invoiceExpense/selectVendorRelatedInformation',success:function(data,textStatus){jQuery('#ajaxVendorRelatedInfoShow').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"vendorId\">"
        dropDown += "<option value=\"\" selected>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"

        if (VendorArr.size()) {
            int tem = 0
            for (int i = 0; i < VendorArr.size(); i++) {

                if (selectIndex == VendorArr[i][0]) {
                    dropDown += "<option value='" + VendorArr[i][0] + "' selected>" + VendorArr[i][1] + " [" + vendorPrefix + VendorArr[i][2] + "]</option>"
                } else {
                    dropDown += "<option value='" + VendorArr[i][0] + "' >" + VendorArr[i][1] + " [" + vendorPrefix + VendorArr[i][2] + "]</option>"
                }

            }
        }
        dropDown += "</select>"
        return dropDown
    }


    def getDebitCreditGlSetupInfo() {
        def listArr = new BudgetViewDatabaseService().executeQuery("SELECT id,creditorGlCode,debitorGlCode,reconcilationGlCode,privateWithdrawalCode FROM DebitCreditGlSetup Where id=1")
        return listArr[0]
    }

    def getDebitCreditGlSetupInfoAutomatic(BusinessCompany businessCompany) {
        def listArr = new BudgetViewDatabaseService()
                .executeQueryAutomatic("SELECT id,creditorGlCode,debitorGlCode,reconcilationGlCode,privateWithdrawalCode FROM DebitCreditGlSetup Where id=1", businessCompany)
        return listArr[0]
    }

    def getPrivateGlSetupInfo() {
        def listArr = new BudgetViewDatabaseService().executeQuery("SELECT private_withdrawal_code,reconcilationGlCode FROM DebitCreditGlSetup Where id=1")
        return listArr[0]
    }


    def getReconcilationAccountDropDown(returnIndex, selectIndex = '0', trackCode = '0', contextCustomer = "") {

        def BankStatementImportFinalArr = new BudgetViewDatabaseService().executeQuery("SELECT id,startTransDate,endTransDate,transBankAccountNo FROM BankStatementImportFinal WHERE  trackCode='" + trackCode + "' ORDER BY id ASC")

        String dropDown = "<select id=\"transBankAccountNo\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BankReconciliation/selectBankRelatedInformation',success:function(data,textStatus){jQuery('#ajaxBankRelatedInfoShow').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"transBankAccountNo\" style=\"width:253px;\">"

        if (BankStatementImportFinalArr.size()) {
            int tem = 0
            for (int i = 0; i < BankStatementImportFinalArr.size(); i++) {

                if (selectIndex == BankStatementImportFinalArr[i][0]) {
                    dropDown += "<option value='" + BankStatementImportFinalArr[i][0] + "' selected>" + BankStatementImportFinalArr[i][3] + "</option>"
                } else {
                    dropDown += "<option value='" + BankStatementImportFinalArr[i][0] + "' >" + BankStatementImportFinalArr[i][3] + "</option>"
                }

            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getReconcilationAccountDropDownTemp(returnIndex, selectIndex = '0', trackCode = '0', contextCustomer = "") {

        def BankStatementImportFinalArr = new BudgetViewDatabaseService().executeQuery("SELECT id,startTransDate,endTransDate,transBankAccountNo FROM BankStatementImport WHERE  trackCode='" + trackCode + "' ORDER BY trans_bank_account_no ASC")

        String dropDown = "<select id=\"transBankAccountNo\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BankReconciliation/selectBankRelatedInformation',success:function(data,textStatus){jQuery('#ajaxBankRelatedInfoShow').html(data);dataGrid('id');},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"transBankAccountNo\">"

        if (BankStatementImportFinalArr.size()) {
            int tem = 0
            for (int i = 0; i < BankStatementImportFinalArr.size(); i++) {

                if (selectIndex == BankStatementImportFinalArr[i][0]) {
                    dropDown += "<option value='" + BankStatementImportFinalArr[i][0] + "' selected>" + BankStatementImportFinalArr[i][3] + "</option>"
                } else {
                    dropDown += "<option value='" + BankStatementImportFinalArr[i][0] + "' >" + BankStatementImportFinalArr[i][3] + "</option>"
                }

            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getCompanySetupReservationTaxAmount() {
        def CompanySetupArr = new BudgetViewDatabaseService().executeQueryAtSingle("SELECT incomeTaxReservation FROM bv.CompanySetup where id=1")

        Integer IncomeTaxReservation = 0
        if (CompanySetupArr.size()) {
            IncomeTaxReservation = CompanySetupArr[0]
        }
        return IncomeTaxReservation
    }

    def getNextGeneratedNumber(fieldName) {
        Integer prefixId = 0
        if (fieldName == "customer") {
            prefixId = 1
        } else if (fieldName == "vendor") {
            prefixId = 2
        } else if (fieldName == "product") {
            prefixId = 3
        } else if (fieldName == "journalEntry") {
            prefixId = 4
        } else if (fieldName == "quickEntry") {
            prefixId = 5
        } else if (fieldName == "budgetExpense") {
            prefixId = 6
        } else if (fieldName == "invoiceExpense") {
            prefixId = 7
        } else if (fieldName == "invoiceIncome") {
            prefixId = 8
        } else if (fieldName == "invoiceInvestment") {
            prefixId = 9
        } else if (fieldName == "internalBanking") {
            prefixId = 10
        } else if (fieldName == "budgetIncome") {
            prefixId = 11
        } else if (fieldName == "receiptEntry") {
            prefixId = 12
        }

        LinkedHashMap gridResultPrefixDataArr
        String selectPrefixDataArr = "prefix,prefixLen As pre_len"
        String selectIndexPrefixDataArr = "prefix,pre_len"
        String fromPrefixDataArr = "SystemPrefix"
        String wherePrefixDataArr = "id=" + prefixId
        String orderByPrefixDataArr = ""
        gridResultPrefixDataArr = new BudgetViewDatabaseService().select(selectPrefixDataArr, fromPrefixDataArr, wherePrefixDataArr, orderByPrefixDataArr, '', 'true', selectIndexPrefixDataArr)

        def PrefixDataArr = gridResultPrefixDataArr['dataGridList']

        //def PrefixDataArr = SystemPrefix.executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=" + prefixId)
        def Prefix = PrefixDataArr[0][0]
        def PrefixLength = PrefixDataArr[0][1]

        LinkedHashMap gridResultNextGeneratedNumberArr
        String selectNextGeneratedNumberArr = fieldName
        String selectIndexNextGeneratedNumberArr = fieldName
        String fromNextGeneratedNumberArr = "NextGeneratedNumber"
        String whereNextGeneratedNumberArr = "id=1"
        String orderByNextGeneratedNumberArr = ""
        gridResultNextGeneratedNumberArr = new BudgetViewDatabaseService().select(selectNextGeneratedNumberArr, fromNextGeneratedNumberArr, whereNextGeneratedNumberArr, orderByNextGeneratedNumberArr, '', 'true', selectIndexNextGeneratedNumberArr)

        def NextGeneratedNumberArr = gridResultNextGeneratedNumberArr['dataGridList'][0]
        //def NextGeneratedNumberArr = NextGeneratedNumber.executeQuery("SELECT " + fieldName + " FROM bv.NextGeneratedNumber where id=1")

        def newSequence = NextGeneratedNumberArr[0] + 1

        def updatedSt = "UPDATE NextGeneratedNumber SET " + fieldName + "=${newSequence} WHERE id=1"
        new BudgetViewDatabaseService().updateByString(updatedSt)
        //NextGeneratedNumber.executeUpdate("UPDATE NextGeneratedNumber SET " + fieldName + "=${newSequence} WHERE id=1")

        def NextGenNo
        if (PrefixLength == 2) {
            NextGenNo = String.format("%02d", newSequence)
        } else if (PrefixLength == 3) {
            NextGenNo = String.format("%03d", newSequence)
        } else if (PrefixLength == 4) {
            NextGenNo = String.format("%04d", newSequence)
        } else if (PrefixLength == 5) {
            NextGenNo = String.format("%05d", newSequence)
        } else if (PrefixLength == 6) {
            NextGenNo = String.format("%06d", newSequence)
        } else if (PrefixLength == 7) {
            NextGenNo = String.format("%07d", newSequence)
        } else if (PrefixLength == 8) {
            NextGenNo = String.format("%08d", newSequence)
        } else if (PrefixLength == 9) {
            NextGenNo = String.format("%09d", newSequence)
        }
        return NextGenNo
    }

    def getNextGeneratedNumberAutomatic(fieldName, BusinessCompany businessCompany) {

        Integer prefixId = 0

        if (fieldName == "customer") {
            prefixId = 1
        } else if (fieldName == "vendor") {
            prefixId = 2
        } else if (fieldName == "product") {
            prefixId = 3
        } else if (fieldName == "journalEntry") {
            prefixId = 4
        } else if (fieldName == "quickEntry") {
            prefixId = 5
        } else if (fieldName == "budgetExpense") {
            prefixId = 6
        } else if (fieldName == "invoiceExpense") {
            prefixId = 7
        } else if (fieldName == "invoiceIncome") {
            prefixId = 8
        } else if (fieldName == "invoiceInvestment") {
            prefixId = 9
        } else if (fieldName == "internalBanking") {
            prefixId = 10
        } else if (fieldName == "budgetIncome") {
            prefixId = 11
        } else if (fieldName == "receiptEntry") {
            prefixId = 12
        }

        LinkedHashMap gridResultPrefixDataArr
        String selectPrefixDataArr = "prefix,prefixLen As pre_len"
        String selectIndexPrefixDataArr = "prefix,pre_len"
        String fromPrefixDataArr = "SystemPrefix"
        String wherePrefixDataArr = "id=" + prefixId
        String orderByPrefixDataArr = ""
        gridResultPrefixDataArr = new BudgetViewDatabaseService().selectAutomatic(selectPrefixDataArr, fromPrefixDataArr,
                                                wherePrefixDataArr, orderByPrefixDataArr, '', 'true',
                                                            selectIndexPrefixDataArr, 0, 0, businessCompany)

        def PrefixDataArr = gridResultPrefixDataArr['dataGridList']

        def Prefix = PrefixDataArr[0][0]
        def PrefixLength = PrefixDataArr[0][1]

        LinkedHashMap gridResultNextGeneratedNumberArr
        String selectNextGeneratedNumberArr = fieldName
        String selectIndexNextGeneratedNumberArr = fieldName
        String fromNextGeneratedNumberArr = "NextGeneratedNumber"
        String whereNextGeneratedNumberArr = "id=1"
        String orderByNextGeneratedNumberArr = ""
        gridResultNextGeneratedNumberArr = new BudgetViewDatabaseService().selectAutomatic(selectNextGeneratedNumberArr,
                fromNextGeneratedNumberArr, whereNextGeneratedNumberArr, orderByNextGeneratedNumberArr, '', 'true',
                                                    selectIndexNextGeneratedNumberArr, 0, 0 , businessCompany)

        def NextGeneratedNumberArr = gridResultNextGeneratedNumberArr['dataGridList'][0]

        def newSequence = NextGeneratedNumberArr[0] + 1

        def updatedSt = "UPDATE NextGeneratedNumber SET " + fieldName + "=${newSequence} WHERE id=1"
        new BudgetViewDatabaseService().updateByStringAutomatic(updatedSt, businessCompany)

        def NextGenNo

        if (PrefixLength == 2) {
            NextGenNo = String.format("%02d", newSequence)
        } else if (PrefixLength == 3) {
            NextGenNo = String.format("%03d", newSequence)
        } else if (PrefixLength == 4) {
            NextGenNo = String.format("%04d", newSequence)
        } else if (PrefixLength == 5) {
            NextGenNo = String.format("%05d", newSequence)
        } else if (PrefixLength == 6) {
            NextGenNo = String.format("%06d", newSequence)
        } else if (PrefixLength == 7) {
            NextGenNo = String.format("%07d", newSequence)
        } else if (PrefixLength == 8) {
            NextGenNo = String.format("%08d", newSequence)
        } else if (PrefixLength == 9) {
            NextGenNo = String.format("%09d", newSequence)
        }

        return NextGenNo
    }


    def getNextGeneratedNumberWithoutPrefix(fieldName) {

        LinkedHashMap gridResultNextGeneratedNumberArr
        String selectNextGeneratedNumberArr = fieldName
        String selectIndexNextGeneratedNumberArr = fieldName
        String fromNextGeneratedNumberArr = "NextGeneratedNumber"
        String whereNextGeneratedNumberArr = "id=1"
        String orderByNextGeneratedNumberArr = ""
        gridResultNextGeneratedNumberArr = new BudgetViewDatabaseService().select(selectNextGeneratedNumberArr, fromNextGeneratedNumberArr, whereNextGeneratedNumberArr, orderByNextGeneratedNumberArr, '', 'true', selectIndexNextGeneratedNumberArr)

        def NextGeneratedNumberArr = gridResultNextGeneratedNumberArr['dataGridList'][0]
        def newSequence = NextGeneratedNumberArr[0] + 1

        def updatedSt = "UPDATE NextGeneratedNumber SET " + fieldName + "=${newSequence} WHERE id=1"
        new BudgetViewDatabaseService().updateByString(updatedSt)
        return newSequence
    }

    def getPreviousBankPaymentID(detailsID) {
        def bankStatementImportDetailsFinalArr = new BudgetViewDatabaseService().executeQueryAtSingle("SELECT (CASE WHEN bank_payment_id IS NULL THEN 'nopay' ELSE bank_payment_id END) AS bank_payment_id FROM bank_statement_import_details_final WHERE id='$detailsID'")
        if (bankStatementImportDetailsFinalArr[0] != 'nopay') {
            return bankStatementImportDetailsFinalArr[0];
        } else {
            return '0'
        }
    }


    def getBudgetIdWhichHasInvoiceIncome() {
        def InvoiceIncomeArr = new BudgetViewDatabaseService().executeQuery("SELECT DISTINCT(budgetItemIncomeId) FROM bv.InvoiceIncome WHERE status=1 AND isReverse=0 AND reverseInvoiceId=0 AND totalGlAmount!=0 ORDER BY budgetItemIncomeId ASC")

        return InvoiceIncomeArr
    }

    def getBudgetIdWhichHasInvoiceExpense() {
        def InvoiceExpenseArr = new BudgetViewDatabaseService().executeQuery("SELECT budget_item_expense_id FROM invoice_expense WHERE status=1 AND is_reverse=0 AND reverse_invoice_id=0 AND total_gl_amount!=0  GROUP BY budget_item_expense_id ORDER BY budget_item_expense_id ASC")
        return InvoiceExpenseArr
    }

    def getBudgetIdWhichHasInvoiceReservation() {
        def InvoiceResArr = new BudgetViewDatabaseService().executeQuery("""SELECT budget_item_id FROM private_reservation_spending_trans
                                                                                WHERE status=1 AND trans_type=5
                                                                                GROUP BY budget_item_id ORDER BY budget_item_id ASC""")
        return InvoiceResArr
    }


    def getPrivateBudgetIdWhichHasInvoice() {
        def InvoicePrivateArr = new BudgetViewDatabaseService().executeQuery("""SELECT budget_item_id FROM private_reservation_spending_trans
                                                                                WHERE status=1 AND trans_type=6
                                                                                GROUP BY budget_item_id ORDER BY budget_item_id ASC""")
        return InvoicePrivateArr
    }


    def dataSourceName() {
        return "bhowmic"
    }


    def getReconciliationBookingType(returnIndex, selectIndex, contextCustomer = "", selectedByBankAccountNo = "", tc = "") {

        def g = new ValidationTagLib()

        def reconTypeArr = new BudgetViewDatabaseService().executeQuery("SELECT id, paymentType,form_type FROM ReconciliationBookingType WHERE status=1")
        String dropDown = "<select id=\"bankPaymentTypeId\" onchange=\"changeTypeOfPayment(this.value);\" name=\"bankPaymentTypeId\" style=\"\">"
//        String dropDown = "<select id=\"bankPaymentTypeId\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BankReconciliation/selectManualReconciliationActionType',success:function(data,textStatus){jQuery('#updateReconciliationInfoArea').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"bankPaymentTypeId\" style=\"\">"

        dropDown += "<option value='0::" + selectedByBankAccountNo + "'>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
        if (reconTypeArr.size()) {
            for (int i = 0; i < reconTypeArr.size(); i++) {
                String strTypeIndex = reconTypeArr[i][0] + "";
                if (strTypeIndex == selectIndex) {
                    dropDown += "<option value='" + reconTypeArr[i][0] + "::" + selectedByBankAccountNo + "::" + tc + "::" + reconTypeArr[i][2] + "' selected='selected'>" + reconTypeArr[i][1] + "</option>"
                } else {
                    dropDown += "<option value='" + reconTypeArr[i][0] + "::" + selectedByBankAccountNo + "::" + tc + "::" + reconTypeArr[i][2] + "'>" + reconTypeArr[i][1] + "</option>"
                }
            }
        } else {
            dropDown += "<option>" + g.message(code: 'coreParamsHelper.noReconciliationBookingTypeSetup.label') + "</option>"
        }

        dropDown += "</select>"
        return dropDown
    }

    def Map getFormTypeFromId(def actionTypeId) {

        Map dataArr = [
                'formType': '',
                'fixedGl' : ''
        ]
        def fromTypeArr = new BudgetViewDatabaseService().executeQuery("SELECT form_type,is_fixed_gl FROM reconciliation_booking_type WHERE id= '${actionTypeId}'")
        if (fromTypeArr) {
            dataArr.formType = fromTypeArr[0][0]
            dataArr.fixedGl = fromTypeArr[0][1]
        }

        return dataArr;

    }

    def getVendorDropDownListForReconciliation(returnIndex, selectIndex = '0', contextCustomer = "") {

        def vendorArr = new BudgetViewDatabaseService().executeQuery("SELECT vm.id,vm.vendorName,vm.vendorCode FROM InvoiceExpense AS budget_exp, VendorMaster As vm  where budget_exp.status=1 AND budget_exp.isReverse=0 AND budget_exp.vendorId=vm.id GROUP BY budget_exp.vendorId")
        String dropDown = "<select id=\"vendorId\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BankReconciliation/selectVendorRelatedInformation',success:function(data,textStatus){jQuery('#divShowReconciliation').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"vendorId\">"
        def vendorPrefix = showGeneratedVendorCode();
        def g = new ValidationTagLib()
        if (vendorArr.size()) {
            dropDown += "<option value='0' >" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
            for (int i = 0; i < vendorArr.size(); i++) {
                if (vendorArr[i][0] == Integer.parseInt(selectIndex)) {
                    dropDown += "<option value='" + vendorArr[i][0] + "' selected='selected'>" + vendorArr[i][1] + " [" + vendorPrefix + "-" + vendorArr[i][2] + "]" + "</option>"
                } else {
                    dropDown += "<option value='" + vendorArr[i][0] + "' >" + vendorArr[i][1] + " [" + vendorPrefix + "-" + vendorArr[i][2] + "]" + "</option>"
                }
            }
        }
        dropDown += "</select>"

        return dropDown
    }
/**
 * for private process grid
 * */
    def getBudgetNameDropDownForPrivateProcess(def selectIndex = 0, def privateSpendingId) {

        def budgetArr = new BudgetViewDatabaseService().executeQuery("""SELECT pbm.id,pbm.budget_name,pbm.budget_code
                                                                        FROM private_budget_master as pbm
                                                                        INNER JOIN private_budget_item as pbi
                                                                        ON pbm.id=pbi.budget_name_id GROUP BY pbm.id ORDER BY pbm.budget_type,pbm.budget_name;""")
//        def budgetArr = new BudgetViewDatabaseService().executeQuery("SELECT id,budget_name,budget_code FROM private_budget_master WHERE `status` = '1' ORDER BY budget_name")
        String dropDown = "<select id='budgetMasterId_${privateSpendingId}'  name='budgetMasterId_${privateSpendingId}'>"
//        String dropDown = "<select id='budgetMasterId_${privateSpendingId}'  name='budgetMasterId_${privateSpendingId}' onchange='onchangeMonthDropDown(${privateSpendingId});'>"
        def privatePrefix = showGeneratedPrivateCode();
        def g = new ValidationTagLib()
        if (budgetArr.size()) {
            dropDown += "<option value='0' >" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
            for (int i = 0; i < budgetArr.size(); i++) {
//                if (budgetArr[i][0] == Integer.parseInt(selectIndex)) {
                if (budgetArr[i][0] == selectIndex) {
                    dropDown += "<option value='" + budgetArr[i][0] + "' selected='selected'>" + budgetArr[i][1] + " [" + privatePrefix + "-" + budgetArr[i][2] + "]" + "</option>"
                } else {
                    dropDown += "<option value='" + budgetArr[i][0] + "' >" + budgetArr[i][1] + " [" + privatePrefix + "-" + budgetArr[i][2] + "]" + "</option>"
                }
            }
        }
        dropDown += "</select>"

        return dropDown
    }

    /**
     * for reservation Invoice grid
     * */
    def getBudgetNameDropDownForReservationInvoice(def selectIndex = 0, def reservationSpendingId) {

        def budgetArr = new BudgetViewDatabaseService().executeQuery("""SELECT rbm.id,rbm.reservation_name,rbm.reservation_code
                                                                        FROM reservation_budget_master as rbm
                                                                        INNER JOIN reservation_budget_item as rbi
                                                                        ON rbm.id = rbi.budget_name_id GROUP BY rbm.id ORDER BY rbm.reservation_type,rbm.reservation_name;""")
//        def budgetArr = new BudgetViewDatabaseService().executeQuery("SELECT id,budget_name,budget_code FROM private_budget_master WHERE `status` = '1' ORDER BY budget_name")
        String dropDown = "<select id='budgetMasterId_${reservationSpendingId}'  name='budgetMasterId_${reservationSpendingId}' onchange='onchangeMonthDropDown(${reservationSpendingId});'>"
//        String dropDown = "<select id='budgetMasterId_${privateSpendingId}'  name='budgetMasterId_${privateSpendingId}' onchange='onchangeMonthDropDown(${privateSpendingId});'>"
        def privatePrefix = showGeneratedReservationCode();
        def g = new ValidationTagLib()
        if (budgetArr.size()) {
            dropDown += "<option value='0' >" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
            for (int i = 0; i < budgetArr.size(); i++) {
//                if (budgetArr[i][0] == Integer.parseInt(selectIndex)) {
                if (budgetArr[i][0] == selectIndex) {
                    dropDown += "<option value='" + budgetArr[i][0] + "' selected='selected'>" + budgetArr[i][1] + " [" + privatePrefix + "-" + budgetArr[i][2] + "]" + "</option>"
                } else {
                    dropDown += "<option value='" + budgetArr[i][0] + "' >" + budgetArr[i][1] + " [" + privatePrefix + "-" + budgetArr[i][2] + "]" + "</option>"
                }
            }
        }
        dropDown += "</select>"

        return dropDown
    }


    def getDropDownNotReconciliationAccountNos(returnIndex, selectIndex = '0', trackCode = '0', contextCustomer = "") {

        def BankStatementImportFinalRootArr = new BudgetViewDatabaseService().executeQuery("SELECT id,bankName " +
                "FROM BankStatementImportFinal WHERE  trackCode='" + trackCode + "' ORDER BY id ASC")

        String tempIdsJoin = ""
        ArrayList tmpIds = new ArrayList()

        if (BankStatementImportFinalRootArr.size()) {
            for (int k = 0; k < BankStatementImportFinalRootArr.size(); k++) {
                tmpIds << BankStatementImportFinalRootArr[k][0]
            }
            tempIdsJoin = tmpIds.join(",")
        }

        ///ALL CUSTOMER BANK ACCOUNT NO///////////
        ArrayList existingAccountArr = new ArrayList()
        /*def CustomerBankAccountArr = new BudgetViewDatabaseService().executeQuery("SELECT id,bankAccountNo FROM CustomerBankAccount where status = 1")
        if (CustomerBankAccountArr.size()) {
            for (int k = 0; k < CustomerBankAccountArr.size(); k++) {
                existingAccountArr << CustomerBankAccountArr[k][1];//.replace(".", "")
            }
        }

        def VendorBankAccountArr = new BudgetViewDatabaseService().executeQuery("SELECT id,bankAccountNo FROM VendorBankAccount where status = 1")
        if (VendorBankAccountArr.size()) {
            for (int k = 0; k < VendorBankAccountArr.size(); k++) {
                existingAccountArr << VendorBankAccountArr[k][1];//.replace(".", "")
            }
        }*/

        def PrivateBankAccountArr = new BudgetViewDatabaseService().executeQuery("""SELECT id, bank_account_no,type FROM
                                                                                private_budget_bank_account where status = 1""")
        if (PrivateBankAccountArr.size()) {
            for (int k = 0; k < PrivateBankAccountArr.size(); k++) {
                existingAccountArr << PrivateBankAccountArr[k][1];//.replace(".", "")
            }
        }

        /*def ReservationBankAccountArr = new BudgetViewDatabaseService().executeQuery("""SELECT id,bank_account_no,type
                                                                                    FROM reservation_budget_bank_account where status = 1""")
        if (ReservationBankAccountArr.size()) {
            for (int k = 0; k < ReservationBankAccountArr.size(); k++) {
                existingAccountArr << ReservationBankAccountArr[k][1];//.replace(".", "")
            }
        }

        def CompanyBankAccountArr = new BudgetViewDatabaseService().executeQuery("SELECT id,bankAccountNo,bank_account_category FROM CompanyBankAccounts where status = 1")
        if (CompanyBankAccountArr.size()) {
            for (int m = 0; m < CompanyBankAccountArr.size(); m++) {
                existingAccountArr << CompanyBankAccountArr[m][1];//.replace(".", "")
            }
        }*/

//        def existingAccountArr = reconciliationService.getAllCustomerBankAccountNo()

        def BankStatementImportFinalArr
        if (tempIdsJoin.isEmpty()) {
            BankStatementImportFinalArr = new BudgetViewDatabaseService().executeQuery("SELECT id,byBankAccountNo " +
                    "FROM BankStatementImportDetailsFinal WHERE reconcilated = 0 GROUP BY byBankAccountNo")
        } else {
            def queryString = "SELECT bsidf.id,by_bank_account_no,cba.bank_account_category FROM bank_statement_import_details_final as bsidf " +
                    "INNER JOIN company_bank_accounts as cba ON cba.bank_account_no = bsidf.trans_bank_account_no " +
                    "WHERE  reconcilated = 0 AND skip_account != 1 AND cba.bank_account_category != 'cba' GROUP BY by_bank_account_no;"

            BankStatementImportFinalArr = new BudgetViewDatabaseService().executeQuery(queryString);
        }


        String dropDown = "<select id=\"noTransBankAccountNo\" onchange=\"getActionType(this.value);\" name=\"noTransBankAccountNo\" style=\"\">"

        if (BankStatementImportFinalArr.size()) {
            for (int i = 0; i < BankStatementImportFinalArr.size(); i++) {
                Integer tempExist = 0
                for (int k = 0; k < existingAccountArr.size(); k++) {
                    if (BankStatementImportFinalArr[i][1] == existingAccountArr[k]) {
                        tempExist = 1
//                        accountNoWithCategory = BankStatementImportFinalArr[i][1] + "-" +  BankStatementImportFinalArr[i][2];
                    }
                }
                def accountNoWithCategory = "";
                if (BankStatementImportFinalArr[i][2]) {
                    accountNoWithCategory = BankStatementImportFinalArr[i][1] + "-" + BankStatementImportFinalArr[i][2];
                } else {
                    accountNoWithCategory = BankStatementImportFinalArr[i][1]
                }

                def dbSelectionIndex = BankStatementImportFinalArr[i][0]
                if (tempExist == 0) {
                    if (selectIndex == dbSelectionIndex.toString()) {
//                        if (BankStatementImportFinalArr[i][1] != "No contra acc") {
                        dropDown += "<option value='" + BankStatementImportFinalArr[i][0] + "' selected>" + accountNoWithCategory + "</option>"
//                        }
                    } else {
//                        if (BankStatementImportFinalArr[i][1] != "No contra acc") {
                        dropDown += "<option value='" + BankStatementImportFinalArr[i][0] + "' >" + accountNoWithCategory + "</option>"
//                        }
                    }
                }

            }
        }

        dropDown += "</select>"
        return dropDown
    }

    def getDropDownNotReconciliationActionType(returnIndex, selectIndex = '1', trackCode = '0', bankImportDetailsId = '0', contextpath = "", iban = "", accountCategory = "cba") {

        def g = new ValidationTagLib()
        ArrayList mapTypeArr = new ArrayList()
        //def accountCategory = "cba";

        /*if(accountCategory == "cba"){
            mapTypeArr = [
                    ['1', 'exVendor', g.message(code: 'coreParamsHelper.addBankAccountToExistingVendor.label')],
                    ['2', 'exCustomer',g.message(code: 'coreParamsHelper.addBankAccountToExistingCustomer.label')],
                    ['3', 'newVendor', g.message(code: 'coreParamsHelper.addNewVendor.label')],
                    ['4', 'newCustomer', g.message(code: 'coreParamsHelper.addNewCustomer.label')],
                    ['5', 'newCompany', g.message(code: 'coreParamsHelper.addCompanyBankAccount.label')],]
        }else if(accountCategory == "pba"){
            mapTypeArr = [
                    ['6', 'exPvtBudget', "Existing Private Budget"],//g.message(code: 'coreParamsHelper.addBankAccountToExistingVendor.label')],
                    ['7', 'newPvtBudget',"New Private Budget"]]//g.message(code: 'coreParamsHelper.addBankAccountToExistingCustomer.label')],
        }*/


        if (accountCategory == "pba") {


            mapTypeArr = [
                    ['6', 'exPvtBudget', "Existing Private Budget"],//g.message(code: 'coreParamsHelper.addBankAccountToExistingVendor.label')],
                    ['7', 'newPvtBudget', "New Private Budget"]]
//g.message(code: 'coreParamsHelper.addBankAccountToExistingCustomer.label')],

        } else {
            mapTypeArr = [
                    ['1', 'exVendor', g.message(code: 'coreParamsHelper.addBankAccountToExistingVendor.label')],
                    ['2', 'exCustomer', g.message(code: 'coreParamsHelper.addBankAccountToExistingCustomer.label')],
                    ['3', 'newVendor', g.message(code: 'coreParamsHelper.addNewVendor.label')],
                    ['4', 'newCustomer', g.message(code: 'coreParamsHelper.addNewCustomer.label')],
                    ['5', 'newCompany', g.message(code: 'coreParamsHelper.addCompanyBankAccount.label')],]
        }




        String dropDown = "<select id=\"noTransActionType\" onchange=\"changeActionType();\" name=\"noTransActionType\" style=\"\">"
//        String dropDown = "<select id=\"noTransActionType\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextpath}/bankReconciliation/selectActionType',success:function(data,textStatus){jQuery('#ajaxActionTypeDiv').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"noTransActionType\" style=\"\">"

        if (mapTypeArr.size()) {
            for (int i = 0; i < mapTypeArr.size(); i++) {

                if (selectIndex == mapTypeArr[i][0]) {
                    dropDown += "<option value='" + mapTypeArr[i][1] + "::" + trackCode + "::" + bankImportDetailsId + "::" + iban + "' selected>" + mapTypeArr[i][2] + "</option>"

                } else {
                    dropDown += "<option value='" + mapTypeArr[i][1] + "::" + trackCode + "::" + bankImportDetailsId + "::" + iban + "' >" + mapTypeArr[i][2] + "</option>"
                }
            }

        }

        dropDown += "</select>"
        return dropDown
    }

    def getDebitCredit(returnIndex, selectIndex = '0', contextCustomer = "") {

        ArrayList mapTypeArr = new ArrayList()
        def g = new ValidationTagLib()

        mapTypeArr = [
                [1, 'D', g.message(code: 'bv.saveManualEntryOfBankStatementDebit.label')],
                [2, 'C', g.message(code: 'bv.saveManualEntryOfBankStatementCredit.label')]
        ]
        String dropDown = "<select id='debitCredit' name='debitCredit' style=\"width:80px;\">"

        if (mapTypeArr.size()) {
            for (int i = 0; i < mapTypeArr.size(); i++) {

                if (selectIndex == mapTypeArr[i][1]) {
                    dropDown += "<option value='" + mapTypeArr[i][1] + "' selected>" + mapTypeArr[i][2] + "</option>"
                } else {
                    dropDown += "<option value='" + mapTypeArr[i][1] + "' >" + mapTypeArr[i][2] + "</option>"
                }
            }

        }
        dropDown += "</select>"
        return dropDown
    }


    def getBookedInvoiceORnot(def selectIndex) {

        ArrayList mapTypeArr = new ArrayList()
        def g = new ValidationTagLib()

        mapTypeArr = [
//                ['Book Invoice',g.message(code: 'dashboardExpanseList.invoice.Button.bookInvoice.label')],
//                ['privateSpend',g.message(code: 'bv.privateBudgetIncomeExpense.dropdown')]

['bookInvoice', g.message(code: 'dashboardExpanseList.invoice.Button.bookInvoice.label')],
['createPDF', g.message(code: 'bv.incomeInvoice.createPDF.dropdown')],
        ]
        String dropDown = "<select id='bookedInvoiceORnot' name='bookedInvoiceORnot' onchange='pdfProcessOrNot();'>"

        if (mapTypeArr.size()) {
            for (int i = 0; i < mapTypeArr.size(); i++) {

                if (selectIndex == mapTypeArr[i][0]) {
                    dropDown += "<option value='" + mapTypeArr[i][0] + "' selected>" + mapTypeArr[i][1] + "</option>"
                } else {
                    dropDown += "<option value='" + mapTypeArr[i][0] + "' >" + mapTypeArr[i][1] + "</option>"
                }
            }

        }
        dropDown += "</select>"
        return dropDown
    }

    /***
     *  This function is used for
     ***/
    def getChartClassTypeGLAccountDropDownAssetLiabilities(returnIndex, selectIndex) {

        def CC = new BudgetViewDatabaseService().executeQuery("SELECT id FROM bv.ChartClass WHERE chartClassType = 1 OR chartClassType = 2")

        def con = ''
        for (int p = 0; p < CC.size(); p++) {
            if (p == 0) {
                con += ' chartClass=' + CC[p]
            } else {
                con += ' OR chartClass=' + CC[p]
            }
        }
        def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,name As categoryName FROM ChartGroup where status=1 AND " + con)
        //def CatArr = ChartGroup.executeQuery("SELECT id,name As categoryName FROM ChartGroup where status=1 AND chartClass=4 OR chartClass=5")
        //def CatArr = ChartGroup.executeQuery("SELECT id,name As categoryName FROM ChartGroup where status=1")
        String dropDown = "<select name='" + returnIndex + "'>"

        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]
                def ProductArr
                ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,accountCode,accountName FROM bv.ChartMaster where status='1' AND chart_group_id='" + catId + "'")
                if (ProductArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < ProductArr.size(); j++) {

                        if (ProductArr[j][1] == selectIndex) {
                            dropDown += "<option value='" + ProductArr[j][1] + "' selected='selected'>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        } else {
                            dropDown += "<option value='" + ProductArr[j][1] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    /***
     *  This function is used for petty cash portion
     ***/
    def getChartClassTypePettyCash(returnIndex, selectIndex = '', contextCustomer = "") {

        def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,name As categoryName FROM ChartGroup where status=1 AND id=7")
        def g = new ValidationTagLib()

        //String dropDown = "<select name='" + returnIndex + "'>"
        String dropDown = "<select id=\"glPettyCash\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BankReconciliation/getBankOpnClsAccount',success:function(data,textStatus){jQuery('#divShowRecn').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"glPettyCash\" style=\" width: 182px;\">"


        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]
                def ProductArr
                ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,accountCode,accountName FROM bv.ChartMaster where status='1' AND chart_group_id='" + catId + "'")
                if (ProductArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    dropDown += "<option value='0' >" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
                    //Select Cash Account
                    for (int j = 0; j < ProductArr.size(); j++) {

                        if (ProductArr[j][1] == selectIndex) {
                            dropDown += "<option value='" + ProductArr[j][1] + "' selected='selected'>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        } else {
                            dropDown += "<option value='" + ProductArr[j][1] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }


    def getTrackCode() {


        def bankStatementImportFinalSelectArr = new BudgetViewDatabaseService().executeQuery("SELECT id,mtFileName,trackCode FROM BankStatementImportFinal GROUP BY mtFileName")

        if (bankStatementImportFinalSelectArr.size()) {

            return bankStatementImportFinalSelectArr[0][2]

        } else {
            Random random = new Random()
            Integer traceCode = 0
            traceCode = random.nextInt(10000000)
            return traceCode
        }

    }

    def getCompanyBankAccountDropDownForManualBankEntry(returnIndex, selectIndex = '', contextCustomer = "") {

        def CatArr = new BudgetViewDatabaseService().executeQueryAtSingle("SELECT bankAccountType FROM CompanyBankAccounts where status=1 GROUP BY bankAccountType")

        String dropDown = "<select id=\"tempTransBankAccountNo\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BankReconciliation/companyBankAccountInformation',success:function(data,textStatus){jQuery('#ajaxCompanyBankAccountDiv').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"tempTransBankAccountNo\" style=\" width: 182px;\">"

        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i]
                def CompanyBankAccArr
                // CompanyBankAccArr = new BudgetViewDatabaseService().executeQuery("SELECT id,bankAccountCode,bankAccountName,bankAccountNo FROM bv.CompanyBankAccounts where status='1' AND bank_account_type='" + catId + "'")
                CompanyBankAccArr = new BudgetViewDatabaseService().executeQuery("SELECT a.id,a.bankAccountCode,a.bankAccountName,a.bankAccountNo FROM bv.CompanyBankAccounts As a,CompanyBankGlRelation AS b where a.status='1' AND a.bank_account_type='" + catId + "' AND a.bank_account_code=b.bank_account_code AND b.gl_chart_code!='' GROUP BY a.bank_account_code ORDER BY a.bank_account_code")

                if (CompanyBankAccArr.size()) {
                    dropDown += "<optgroup label='" + catId + "'>"
                    for (int j = 0; j < CompanyBankAccArr.size(); j++) {

                        if (CompanyBankAccArr[j][1] == selectIndex) {
                            dropDown += "<option value='" + CompanyBankAccArr[j][1] + "' selected='selected'>" + CompanyBankAccArr[j][3] + " - " + CompanyBankAccArr[j][2] + "</option>"
                        } else {
                            dropDown += "<option value='" + CompanyBankAccArr[j][1] + "' >" + CompanyBankAccArr[j][3] + " - " + CompanyBankAccArr[j][2] + "</option>"
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    ///////////////// ManualBank //////////////////////////////
    def getCompanyBankAccountDropDownForManualBank(returnIndex, selectIndex = "", contextPath = "") {

        def CatArr = new BudgetViewDatabaseService().executeQueryAtSingle("SELECT bankAccountType FROM CompanyBankAccounts where status=1 GROUP BY bankAccountType")
        def g = new ValidationTagLib()

//        String dropDown = "<select id='" + returnIndex + "' name='" + returnIndex + "' required='required' style=\" \">"

//        String dropDown = "<select id=\"tempTransBankAccountNo\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextPath}/ManualEntryOfBankStatement/selectStartingBalance',success:function(data,textStatus){jQuery('#startingBalance').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"tempTransBankAccountNo\" style=\"width:253px;\">"
        String dropDown = "<select required=\"\" id=\"tempTransBankAccountNo\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextPath}/ManualEntryOfBankStatement/selectStartingBalance',success:function(data,textStatus){jQuery('#openCloseForm').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"tempTransBankAccountNo\" style=\"width:253px;\">"
        dropDown += "<option value=''>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"

        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i]
                def CompanyBankAccArr
                CompanyBankAccArr = new BudgetViewDatabaseService().executeQuery("SELECT a.id,a.bankAccountCode,a.bankAccountName,a.bankAccountNo FROM bv.CompanyBankAccounts As a,CompanyBankGlRelation AS b where a.status='1' AND a.bank_account_type='" + catId + "' AND a.bank_account_code=b.bank_account_code AND b.gl_chart_code!='' GROUP BY a.bank_account_code ORDER BY a.bank_account_code")
                if (CompanyBankAccArr.size()) {
                    dropDown += "<optgroup label='" + catId + "'>"
                    for (int j = 0; j < CompanyBankAccArr.size(); j++) {

                        if (CompanyBankAccArr[j][3] == selectIndex) {
                            //dropDown += "<option value='" + CompanyBankAccArr[j][3] + "-" + CompanyBankAccArr[j][2] + "' selected='selected'>" + CompanyBankAccArr[j][3] + " - " + CompanyBankAccArr[j][2] + "</option>"
                            dropDown += "<option value='" + CompanyBankAccArr[j][3] + "::" + CompanyBankAccArr[j][2] + "' selected='selected'>" + CompanyBankAccArr[j][3] + " - " + CompanyBankAccArr[j][2] + "</option>"
                        } else {
                            dropDown += "<option value='" + CompanyBankAccArr[j][3] + "::" + CompanyBankAccArr[j][2] + "' >" + CompanyBankAccArr[j][3] + " - " + CompanyBankAccArr[j][2] + "</option>"
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }

        dropDown += "</select>"
        return dropDown
    }

//    def getCompanyBankAccountDropDownForManualBank(returnIndex, selectIndex = '') {
//
//        def CatArr = new BudgetViewDatabaseService().executeQueryAtSingle("SELECT bankAccountType FROM CompanyBankAccounts where status=1 GROUP BY bankAccountType")
//        def g=new ValidationTagLib()
//        String dropDown = "<select id='" + returnIndex + "' name='" + returnIndex + "' required='required' style=\" width: 182px;\">"
//        dropDown += "<option value=''>"+g.message(code:'bv.undoReconciliation.Select.label')+"</option>"
//        if (CatArr.size()) {
//            for (int i = 0; i < CatArr.size(); i++) {
//                def catId = CatArr[i]
//                def CompanyBankAccArr
//                CompanyBankAccArr = new BudgetViewDatabaseService().executeQuery("SELECT a.id,a.bankAccountCode,a.bankAccountName,a.bankAccountNo FROM bv.CompanyBankAccounts As a,CompanyBankGlRelation AS b where a.status='1' AND a.bank_account_type='" + catId + "' AND a.bank_account_code=b.bank_account_code AND b.gl_chart_code!='' GROUP BY a.bank_account_code ORDER BY a.bank_account_code")
//                if (CompanyBankAccArr.size()) {
//                    dropDown += "<optgroup label='" + catId + "'>"
//                    for (int j = 0; j < CompanyBankAccArr.size(); j++) {
//
//                        if (CompanyBankAccArr[j][3] == selectIndex) {
//                            dropDown += "<option value='" + CompanyBankAccArr[j][3] + "' selected='selected'>" + CompanyBankAccArr[j][3] + " - " + CompanyBankAccArr[j][2] + "</option>"
//                        } else {
//                            dropDown += "<option value='" + CompanyBankAccArr[j][3] + "' >" + CompanyBankAccArr[j][3] + " - " + CompanyBankAccArr[j][2] + "</option>"
//                        }
//                    }
//                    dropDown += "</optgroup>"
//                }
//            }
//        }
//        dropDown += "</select>"
//        return dropDown
//    }

    def getFirstCompanyBankAccountForManualBankEntry() {

        def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT bankAccountType FROM CompanyBankAccounts where status=1 GROUP BY bankAccountType")

        String dropDown = ""
        if (CatArr.size()) {
            def catId = CatArr[0]
            def CompanyBankAccArr
            CompanyBankAccArr = new BudgetViewDatabaseService().executeQuery("SELECT id,bankAccountCode,bankAccountName FROM bv.CompanyBankAccounts where status='1' AND bank_account_type='" + catId + "'")
            if (CompanyBankAccArr.size()) {
                dropDown = CompanyBankAccArr[0][1]
            }
        }
        return dropDown
    }

    def getTransNumber() {
        Random random = new Random()
        Integer traceCode = 0
        traceCode = random.nextInt(100000)
        Integer traceCode2 = 0
        traceCode2 = random.nextInt(100)
        def TransNumber = traceCode + "/" + traceCode2
        return TransNumber
    }

    def getTransactionCode() {
        Random random = new Random()
        Integer traceCode = 0
        traceCode = random.nextInt(1000)
        return traceCode
    }

    def getClosingAndOpeningBalanceByGL(bankAccountGlCode = '') {

        double companyBankOpeningBalance = 0.00
        double companyBankClosingBalance = 0.00

        if (bankAccountGlCode) {
            def ActiveFiscalYear = getActiveFiscalYear()
            def FiscalYearInfo = getActiveFiscalYearInformation(ActiveFiscalYear)

            def tempStartDate = FiscalYearInfo[0][7]
            def tempEndDate = FiscalYearInfo[0][8]


            def startDate = FiscalYearInfo[0][4] + "-" + tempStartDate.substring(2, 4) + "-" + tempStartDate.substring(4, 6)
            def endDate = FiscalYearInfo[0][6] + "-" + tempEndDate.substring(2, 4) + "-" + tempEndDate.substring(4, 6)

            ////////////Opening Balance/////////////////////
            def companyBankOpeningBalanceArr = new BudgetViewDatabaseService().executeQueryAtSingle("SELECT SUM(amount) AS openingBalance FROM TransMaster WHERE accountCode='$bankAccountGlCode' AND transDate<'$startDate'")




            if (companyBankOpeningBalanceArr[0]) {
                companyBankOpeningBalance = companyBankOpeningBalanceArr[0]
            }

            ////////////Closing Balance/////////////////////
            def companyBankClosingBalanceArr = new BudgetViewDatabaseService().executeQueryAtSingle("SELECT SUM(amount) AS closingBalance FROM TransMaster WHERE accountCode='$bankAccountGlCode' AND transDate>='$startDate' AND transDate<='$endDate'")

            if (companyBankClosingBalanceArr[0]) {
                companyBankClosingBalance = companyBankClosingBalanceArr[0]
            }
        }

        Map map = ["companyBankOpeningBalance": 0.0, "companyBankClosingBalance": 0.0]

        map.companyBankOpeningBalance = companyBankOpeningBalance
        map.companyBankClosingBalance = companyBankClosingBalance
        return map

    }

    def showCountryName(id) {
        def countryNameArr = new BudgetViewDatabaseService().executeQuery("SELECT id,NAME FROM bv.Countries where id=" + id)
        def cNameArr = countryNameArr[0][1]
        return cNameArr
    }


    def getBusinessCompany(returnIndex, selectIndex) {

        def businessCompanyArr = BusinessCompany.executeQuery("SELECT id, name FROM BusinessCompany WHERE status=1 group by name ORDER BY name ASC")

        String dropDown = "<select name='" + returnIndex + "' >"

        if (businessCompanyArr.size()) {
            int tem = 0
            for (int i = 0; i < businessCompanyArr.size(); i++) {

                if (selectIndex == businessCompanyArr[i][0]) {
                    dropDown += "<option value='" + businessCompanyArr[i][0] + "' selected>" + businessCompanyArr[i][1] + "</option>"
                } else {
                    dropDown += "<option value='" + businessCompanyArr[i][0] + "' >" + businessCompanyArr[i][1] + "</option>"
                }

            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def paymentTermDropdownList(returnIndex, selectIndex) {
        if (selectIndex) {
            selectIndex = Integer.parseInt(selectIndex.toString())
        }

        def paymentTermsArr = new BudgetViewDatabaseService().executeQuery("SELECT id, terms FROM PaymentTerms WHERE status=1 ORDER BY id ASC")
        String dropDown = "<select class=\"styled sidebr01 termsIdJs\" id=\"${returnIndex}\" name=\"${returnIndex}\">"

        if (paymentTermsArr.size()) {
            int tem = 0
            for (int i = 0; i < paymentTermsArr.size(); i++) {

                if (selectIndex == paymentTermsArr[i][0]) {
                    dropDown += "<option value='" + paymentTermsArr[i][0] + "' selected>" + paymentTermsArr[i][1] + "</option>"
                } else {
                    dropDown += "<option value='" + paymentTermsArr[i][0] + "' >" + paymentTermsArr[i][1] + "</option>"
                }

            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getTheNumberOfDayAccordingToPaymentTerm(id) {
        def numberOfDays = new BudgetViewDatabaseService().executeQuery("SELECT daysBeforeDue FROM bv.PaymentTerms where id=" + id)
        def nDayArr = numberOfDays[0][1]
        return nDayArr
    }


    def getExpanseBudgetForReconcilationDown(returnIndex, selectValName, contextCustomer = "", selectedByBankAccountNo = "", privateSp, budgetItem = "") {

        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

        LinkedHashMap gridResultVendorArr
        String select = "DISTINCT(a.vendorId) AS vendor_id,v.vendorName As vendor_name"
        String selectIndex = "vendor_id,vendor_name"
        String from = "BudgetItemExpense AS a,VendorMaster AS v "
        String where = "a.bookingPeriodStartMonth >='" + FiscalYearInfo[0][3] + "' AND a.bookingPeriodStartYear ='" + FiscalYearInfo[0][4] + "' AND a.bookingPeriodStartMonth <='" + FiscalYearInfo[0][5] + "' AND a.bookingPeriodStartYear ='" + FiscalYearInfo[0][6] + "' AND a.vendorId=v.id AND a.vendorId!='' AND a.status=1"
        String orderBy = "v.vendorName ASC"
        gridResultVendorArr = new BudgetViewDatabaseService().select(select, from, where, orderBy, '', 'true', selectIndex)

        def expanseBudgetArr = gridResultVendorArr['dataGridList']

        //String dropDown = "<select name='" + returnIndex + "' >"
        String dropDown = "<select required=\"\" id=\"${returnIndex}\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BankReconciliation/budgetCodeWiseExpanseInvoice',success:function(data,textStatus){jQuery('#ajaxbudgetCodeWiseExpanseInvoiceDiv').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"${returnIndex}\" style=\" \">"
//        String dropDown = "<select required=\"\" id=\"${returnIndex}\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BankReconciliation/budgetCodeWiseExpanseInvoice',success:function(data,textStatus){jQuery('#ajaxbudgetCodeWiseExpanseInvoiceDiv').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"${returnIndex}\" style=\" \">"

        def g = new ValidationTagLib()
        if (expanseBudgetArr.size()) {
            dropDown += "<option value='0' >" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
            int tem = 0
            for (int i = 0; i < expanseBudgetArr.size(); i++) {
                if (selectValName == expanseBudgetArr[i][0]) {

                    if (budgetItem == expanseBudgetArr[i][0]) {
                        dropDown += "<option value='" + expanseBudgetArr[i][0] + "::" + selectedByBankAccountNo + "::" + privateSp + "' selected>" + expanseBudgetArr[i][1] + "</option>"
                    } else if (selectedByBankAccountNo.trim()) {
                        dropDown += "<option value='" + expanseBudgetArr[i][0] + "::" + selectedByBankAccountNo + "::" + privateSp + "' selected>" + expanseBudgetArr[i][1] + "</option>"
                    } else {
                        dropDown += "<option value='" + expanseBudgetArr[i][0] + "::" + privateSp + "' selected>" + expanseBudgetArr[i][1] + "</option>"
                    }

                } else {
                    if (budgetItem == expanseBudgetArr[i][0]) {
                        dropDown += "<option value='" + expanseBudgetArr[i][0] + "::" + selectedByBankAccountNo + "::" + privateSp + "' selected>" + expanseBudgetArr[i][1] + "</option>"
                    } else if (selectedByBankAccountNo.trim()) {
                        dropDown += "<option value='" + expanseBudgetArr[i][0] + "::" + selectedByBankAccountNo + "::" + privateSp + "' >" + expanseBudgetArr[i][1] + "</option>"
                    } else {
                        dropDown += "<option value='" + expanseBudgetArr[i][0] + "::" + privateSp + "' >" + expanseBudgetArr[i][1] + "</option>"
                    }

                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getIncomeNExpenseBudgetDropdown(returnIndex, selectedIndex) {

        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

        String strQuery = """SELECT * FROM (
                            SELECT DISTINCT(a.customer_id) AS vendorId,v.customer_name As budgetName from budget_item_income AS a,customer_master AS v
                            WHERE a.booking_period_start_year ='${FiscalYearInfo[0][4]}' AND a.customer_id=v.id AND a.customer_id!='' AND a.status=1
                            ORDER BY v.customer_name ASC
                            ) as tblouter
                            UNION
                            SELECT * FROM (
                            SELECT DISTINCT(a.vendor_id) AS vendorId,v.vendor_name As budgetName from budget_item_expense AS a,vendor_master AS v
                            WHERE a.booking_period_start_year ='${FiscalYearInfo[0][4]}' AND a.vendor_id=v.id AND a.vendor_id!='' AND a.status=1
                            ORDER BY v.vendor_name ASC
                            ) as tblouter""";

        def expanseBudgetArr = new BudgetViewDatabaseService().executeQuery(strQuery);

        String dropDown = "<select id=\"${returnIndex}\" name=\"${returnIndex}\" requi=red>"
        def g = new ValidationTagLib()
        if (expanseBudgetArr.size()) {
            dropDown += "<option value='' >" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
            int tem = 0
            for (int i = 0; i < expanseBudgetArr.size(); i++) {
                if (selectedIndex == expanseBudgetArr[i][1]) {
                    dropDown += "<option value='${expanseBudgetArr[i][1]}' selected>" + expanseBudgetArr[i][1] + "</option>"
                } else {
                    dropDown += "<option value='${expanseBudgetArr[i][1]}' >" + expanseBudgetArr[i][1] + "</option>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }


    def getCustomerNVendorDropdown(returnIndex, selectedIndex) {

        String strQuery = """SELECT id, customer_name AS vendorName  FROM customer_master where `status` = '1'
                            UNION
                            SELECT id, vendor_name AS vendorName FROM vendor_master where `status` = '1'""";

        def expanseBudgetArr = new BudgetViewDatabaseService().executeQuery(strQuery);

        String dropDown = "<select id=\"${returnIndex}\" name=\"${returnIndex}\" requi=red>"
        def g = new ValidationTagLib()
        if (expanseBudgetArr.size()) {
            dropDown += "<option value='' >" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
            int tem = 0
            for (int i = 0; i < expanseBudgetArr.size(); i++) {
                if (selectedIndex == expanseBudgetArr[i][1]) {
                    dropDown += "<option value='${expanseBudgetArr[i][1]}' selected>" + expanseBudgetArr[i][1] + "</option>"
                } else {
                    dropDown += "<option value='${expanseBudgetArr[i][1]}' >" + expanseBudgetArr[i][1] + "</option>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

//  ////////////////////////////////////////////////////////////////////

    def getReservationBudgetForReconcilationDown(returnIndex, selectValName, contextCustomer = "", selectedByBankAccountNo = "", privateSp) {

        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

//        LinkedHashMap gridResultVendorArr
//        String select = "DISTINCT(a.budget_name_id) AS vendor_id,v.reservation_name As vendor_name"
//        String selectIndex = "vendor_id,vendor_name"
//        String from = "reservation_budget_item AS a,reservation_budget_master AS v "
//        String where = " a.booking_period_month >='" + FiscalYearInfo[0][3] + "' AND a.booking_period_year >='" + FiscalYearInfo[0][4] + "'  AND a.budget_name_id=v.id  AND a.status=1"
//        String orderBy = "v.reservation_name ASC"
//        gridResultVendorArr = new BudgetViewDatabaseService().select(select, from, where, orderBy, '', 'true', selectIndex)

        String strQuery = """ SELECT DISTINCT(rbi.budget_name_id) AS budgetId,rbm.reservation_name As budgetName
                            FROM reservation_budget_item as rbi
                            INNER JOIN  reservation_budget_master as rbm ON rbi.budget_name_id = rbm.id
                            WHERE rbi.booking_period_year ='${FiscalYearInfo[0][4]}' AND rbi.status=1
                            ORDER BY rbm.reservation_name ASC""";

        def expanseBudgetArr = new BudgetViewDatabaseService().executeQuery(strQuery);
        //def expanseBudgetArr = gridResultVendorArr['dataGridList']

        //String dropDown = "<select name='" + returnIndex + "' >"
        String dropDown = "<select id=\"${returnIndex}\" onchange=\"onChangeReservationBudgetItem(this.value);\" name=\"${returnIndex}\" requi=red>"
//        String dropDown = "<select required=\"\" id=\"${returnIndex}\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BankReconciliation/budgetCodeWiseReservation',success:function(data,textStatus){jQuery('#ajaxbudgetCodeWiseExpanseInvoiceDiv').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"${returnIndex}\" style=\" \">"
//        String dropDown = "<select required=\"\" id=\"${returnIndex}\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BankReconciliation/budgetCodeWiseExpanseInvoice',success:function(data,textStatus){jQuery('#ajaxbudgetCodeWiseExpanseInvoiceDiv').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"${returnIndex}\" style=\" \">"

        def g = new ValidationTagLib()
        if (expanseBudgetArr.size()) {
            dropDown += "<option value='" + '0' + "::" + selectedByBankAccountNo + "::" + '0' + "' >" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
//            dropDown += "<option value='0' >"+g.message(code:'bv.undoReconciliation.Select.label')+"</option>"
            int tem = 0
            for (int i = 0; i < expanseBudgetArr.size(); i++) {
                if (selectValName == expanseBudgetArr[i][0]) {

                    if (selectedByBankAccountNo.trim()) {
                        dropDown += "<option value='" + expanseBudgetArr[i][0] + "::" + selectedByBankAccountNo + "::" + privateSp + "' selected>" + expanseBudgetArr[i][1] + "</option>"
                    } else {
                        dropDown += "<option value='" + expanseBudgetArr[i][0] + "::" + privateSp + "' selected>" + expanseBudgetArr[i][1] + "</option>"
                    }

                } else {
                    if (selectedByBankAccountNo.trim()) {
                        dropDown += "<option value='" + expanseBudgetArr[i][0] + "::" + selectedByBankAccountNo + "::" + privateSp + "' >" + expanseBudgetArr[i][1] + "</option>"
                    } else {
                        dropDown += "<option value='" + expanseBudgetArr[i][0] + "::" + privateSp + "' >" + expanseBudgetArr[i][1] + "</option>"
                    }

                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }


    def getPrivateSpendingForReconcilationDown(returnIndex, selectValName, contextPath = "", selectedByBankAccountNo = "", privateSp) {

        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

        String strQuery = """ SELECT DISTINCT(pbm.id) AS budgetId,pbm.budget_name As budgetName,pbm.budget_type from  private_budget_master AS pbm
                              INNER JOIN private_budget_item as pbi ON pbm.id = pbi.budget_name_id
                              WHERE pbi.booking_period <= ${FiscalYearInfo[0][5]} AND pbi.booking_year <= ${
            FiscalYearInfo[0][6]
        } AND pbm.status = 1
                              ORDER BY pbm.budget_type,pbm.budget_name """

        /*String select = "DISTINCT(a.id) AS budgetId,a.budgetName As budgetName,a.budget_type"
        String selectIndex = "budgetId,budgetName,budget_type"
        String from = "private_budget_master AS a,private_budget_item AS b "
        String where = " b.bookingPeriod <='" + FiscalYearInfo[0][5] + "' AND b.bookingYear ='" + FiscalYearInfo[0][6] + "' AND b.budgetNameId=a.id  AND a.status=1 "
        String orderBy = " a.budgetName ASC"*/
        def privateBudgetArr = new BudgetViewDatabaseService().executeQuery(strQuery)

        //String dropDown = "<select name='" + returnIndex + "' >"
        String dropDown = "<select required=\"\" id=\"${returnIndex}\" onchange=\"jQuery.ajax({type:'POST',data: {id: this.value}, url:'${contextPath}/BankReconciliation/budgetCodeWisePrivateSpending',success:function(data,textStatus){jQuery('#ajaxbudgetCodeWiseExpanseInvoiceDiv').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"${returnIndex}\" >"
        String budgetTypeOld = ""

        def g = new ValidationTagLib()
        if (privateBudgetArr.size()) {
            dropDown += "<option value='0' selected >" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
            //int tem = 0
            for (int i = 0; i < privateBudgetArr.size(); i++) {
                String budgetType = ""
                if (privateBudgetArr[i][2] == 1) {
                    budgetType = 'Income'
                } else if (privateBudgetArr[i][2] == 2) {
                    budgetType = 'Fixed Expense'
                } else if (privateBudgetArr[i][2] == 3) {
                    budgetType = 'Variable Expense'
                }

                if (budgetTypeOld != budgetType) {
                    if (budgetTypeOld != "") {
                        dropDown += "</optgroup>"
                    }
                    budgetTypeOld = budgetType
                    dropDown += "<optgroup label='" + budgetType + "'>"
                }

                if (selectValName == privateBudgetArr[i][0]) {

                    if (selectedByBankAccountNo.trim()) {
                        dropDown += "<option value='" + privateBudgetArr[i][0] + "::" + selectedByBankAccountNo + "::" + privateSp + "' selected>" + privateBudgetArr[i][1] + "</option>"
                    } else {
                        dropDown += "<option value='" + privateBudgetArr[i][0] + "::" + privateSp + "' selected>" + privateBudgetArr[i][1] + "</option>"
                    }

                } else {
                    if (selectedByBankAccountNo.trim()) {
                        dropDown += "<option value='" + privateBudgetArr[i][0] + "::" + selectedByBankAccountNo + "::" + privateSp + "' >" + privateBudgetArr[i][1] + "</option>"
                    } else {
                        dropDown += "<option value='" + privateBudgetArr[i][0] + "::" + privateSp + "' >" + privateBudgetArr[i][1] + "</option>"
                    }
                }
            }
            dropDown += "</optgroup>"
        }

        dropDown += "</select>"
        return dropDown
    }

    def getExpanseBudgetMonthForReconcilationDown(returnIndex, selectIndex = '', budgetItemId = "", budgetGLCode = "", bookingYear = "") {

        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)
        def bookingYearFinal
        if (bookingYear == '') {
            bookingYearFinal = FiscalYearInfo[0][4]
        } else {
            bookingYearFinal = bookingYear
        }
        def sqlQuery = """SELECT a.booking_period_start_month,a.booking_period_start_year FROM budget_item_expense AS a
                      LEFT JOIN budget_item_expense_details AS b ON a.id=b.budget_item_expense_id
                      WHERE a.vendor_id='${budgetItemId}' AND a.booking_period_start_year='${bookingYearFinal}'
                       GROUP BY a.booking_period_start_month """
//        def sqlQuery= """SELECT a.booking_period_start_month,a.booking_period_start_year FROM budget_item_expense AS a
//                      LEFT JOIN budget_item_expense_details AS b ON a.id=b.budget_item_expense_id
//                      WHERE a.vendor_id='${budgetItemId}' AND b.gl_account='${budgetGLCode}'
//                      AND a.booking_period_start_year='${FiscalYearInfo[0][4]}' GROUP BY a.booking_period_start_month """
        def expanseBudgetArr = new BudgetViewDatabaseService().executeQuery(sqlQuery);

//        println("SELECT a.booking_period_start_month,a.booking_period_start_year FROM budget_item_expense AS a LEFT JOIN budget_item_expense_details AS b ON a.id=b.budget_item_expense_id WHERE a.vendor_id='" + budgetItemId + "' AND b.gl_account='" + budgetGLCode + "' AND a.booking_period_start_year='" + FiscalYearInfo[0][4] + "' GROUP BY a.booking_period_start_month ")
        String dropDown = "<select name='" + returnIndex + "' >"

        if (expanseBudgetArr.size()) {

            def selectIndexFinal
            if (selectIndex == '') {
                selectIndexFinal = 1
            } else {
                selectIndexFinal = Integer.parseInt(selectIndex.toString())
            }
            def finalResult = getBookingPeriodForManualReconciliation(expanseBudgetArr, selectIndexFinal)

            int tem = 0
            for (int i = 0; i < expanseBudgetArr.size(); i++) {
                if (finalResult == expanseBudgetArr[i][0]) {

                    if (expanseBudgetArr[i][0] == 12) {
                        dropDown += "<option value='" + expanseBudgetArr[i][0] + "-" + expanseBudgetArr[i][1] + "' selected>" + "Dec" + "-" + expanseBudgetArr[i][1] + "</option>"
                    } else {
                        dropDown += "<option value='" + expanseBudgetArr[i][0] + "-" + expanseBudgetArr[i][1] + "' selected>" + monthNameShow(expanseBudgetArr[i][0]) + "-" + expanseBudgetArr[i][1] + "</option>"
                    }

                } else {
                    if (expanseBudgetArr[i][0] == 12) {
                        dropDown += "<option value='" + expanseBudgetArr[i][0] + "-" + expanseBudgetArr[i][1] + "' >" + "Dec" + "-" + expanseBudgetArr[i][1] + "</option>"
                    } else {
                        dropDown += "<option value='" + expanseBudgetArr[i][0] + "-" + expanseBudgetArr[i][1] + "' >" + monthNameShow(expanseBudgetArr[i][0]) + "-" + expanseBudgetArr[i][1] + "</option>"
                    }

                }

            }
        }
        dropDown += "</select>"
        return dropDown

    }

    def getBookingPeriodForManualReconciliation(def expanseBudgetArr, def selectIndex) {
        def res = checkValueIn2DArray(expanseBudgetArr, selectIndex)
        int[] quater1 = [1, 2, 3, 4];
        int[] quater2 = [5, 6, 7, 8];
        int[] quater3 = [9, 10, 11, 12];
        def quater
        def finalResult = 1
        if (res) {
            return selectIndex
        } else {
            if (quater1.contains(selectIndex)) {
                quater = quater1
            } else if (quater2.contains(selectIndex)) {
                quater = quater2
            } else if (quater3.contains(selectIndex)) {
                quater = quater3
            }

            def tempselectindex = selectIndex - 1
            def indicator = "M"
            for (int i = 0; i < quater.size(); i++) {
                if (tempselectindex >= quater[0] && tempselectindex <= quater[3]) {
                    def checkRes = checkValueIn2DArray(expanseBudgetArr, tempselectindex)
                    if (checkRes) {
                        finalResult = tempselectindex
                        break
                    } else {
                        if (indicator == "M") {
                            tempselectindex = tempselectindex - 1
                        } else {
                            tempselectindex = tempselectindex + 1
                        }
                    }
                } else {
                    tempselectindex = selectIndex + 1
                    indicator = "P"
                }

            }

        }
        return finalResult;
    }

    def checkValueIn2DArray(def expanseBudgetArr, def selectIndex) {
        def flagMatch = false

        for (int i = 0; i < expanseBudgetArr.size(); i++) {

            if (selectIndex == expanseBudgetArr[i][0]) {
                flagMatch = true
                break
            } else {
            }

        }

        return flagMatch
    }


    def getReservationBudgetMonthForReconcilationDown(returnIndex, selectIndex = '', budgetItemId = "", budgetGLCode = "", bookingYear = "") {

        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)
        def bookingYearFinal
        if (bookingYear == '') {
            bookingYearFinal = FiscalYearInfo[0][4]
        } else {
            bookingYearFinal = bookingYear
        }
        def expanseBudgetArr = new BudgetViewDatabaseService().executeQuery("SELECT a.booking_period_month,a.booking_period_year " +
                "FROM reservation_budget_item AS a LEFT JOIN reservation_budget_item_details AS b ON a.id=b.reserv_budget_item_id  " +
                "WHERE a.budget_name_id='" + budgetItemId + "' AND b.gl_account='" + budgetGLCode + "' AND a.booking_period_year='" + bookingYearFinal + "'" +
                " GROUP BY a.booking_period_month ")
//        def expanseBudgetArr = new BudgetViewDatabaseService().executeQuery("SELECT a.booking_period_start_month,a.booking_period_start_year FROM budget_item_expense AS a LEFT JOIN budget_item_expense_details AS b ON a.id=b.budget_item_expense_id WHERE a.vendor_id='" + budgetItemId + "' AND b.gl_account='" + budgetGLCode + "' AND a.booking_period_start_year='" + FiscalYearInfo[0][4] + "' GROUP BY a.booking_period_start_month ")

        String dropDown = "<select required=\"\" name='" + returnIndex + "' >"

        if (expanseBudgetArr.size()) {
            int tem = 0
            for (int i = 0; i < expanseBudgetArr.size(); i++) {
                if (selectIndex == expanseBudgetArr[i][0]) {

                    if (expanseBudgetArr[i][0] == 12) {
                        dropDown += "<option value='" + expanseBudgetArr[i][0] + "-" + expanseBudgetArr[i][1] + "' selected>" + "Dec" + "-" + expanseBudgetArr[i][1] + "</option>"
                    } else {
                        dropDown += "<option value='" + expanseBudgetArr[i][0] + "-" + expanseBudgetArr[i][1] + "' selected>" + monthNameShow(expanseBudgetArr[i][0]) + "-" + expanseBudgetArr[i][1] + "</option>"
                    }

                } else {
                    if (expanseBudgetArr[i][0] == 12) {
                        dropDown += "<option value='" + expanseBudgetArr[i][0] + "-" + expanseBudgetArr[i][1] + "' >" + "Dec" + "-" + expanseBudgetArr[i][1] + "</option>"
                    } else {
                        dropDown += "<option value='" + expanseBudgetArr[i][0] + "-" + expanseBudgetArr[i][1] + "' >" + monthNameShow(expanseBudgetArr[i][0]) + "-" + expanseBudgetArr[i][1] + "</option>"
                    }

                }

            }
        }
        dropDown += "</select>"
        return dropDown

    }

    def getPrivateSpendingPeriodForReconcilationDown(returnIndex, selectIndex = '', budgetItemId = "", bookingYear = "") {

        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

        def bookingYearFinal
        if (bookingYear == '') {
            bookingYearFinal = FiscalYearInfo[0][4]
        } else {
            bookingYearFinal = bookingYear
        }

        def expanseBudgetArr = new BudgetViewDatabaseService().executeQuery("SELECT a.booking_period,a.booking_year FROM private_budget_item AS a where a.budget_name_id='" + budgetItemId + "' AND a.booking_year='" + bookingYearFinal + "'GROUP BY a.booking_period")

        String dropDown = "<select name='" + returnIndex + "' >"

        if (expanseBudgetArr.size()) {
            int tem = 0
            for (int i = 0; i < expanseBudgetArr.size(); i++) {
                if (selectIndex == expanseBudgetArr[i][0]) {

                    if (expanseBudgetArr[i][0] == 12) {
                        dropDown += "<option value='" + expanseBudgetArr[i][0] + "-" + expanseBudgetArr[i][1] + "' selected>" + "Dec" + "-" + expanseBudgetArr[i][1] + "</option>"
                    } else {
                        dropDown += "<option value='" + expanseBudgetArr[i][0] + "-" + expanseBudgetArr[i][1] + "' selected>" + monthNameShow(expanseBudgetArr[i][0]) + "-" + expanseBudgetArr[i][1] + "</option>"
                    }

                } else {
                    if (expanseBudgetArr[i][0] == 12) {
                        dropDown += "<option value='" + expanseBudgetArr[i][0] + "-" + expanseBudgetArr[i][1] + "' >" + "Dec" + "-" + expanseBudgetArr[i][1] + "</option>"
                    } else {
                        dropDown += "<option value='" + expanseBudgetArr[i][0] + "-" + expanseBudgetArr[i][1] + "' >" + monthNameShow(expanseBudgetArr[i][0]) + "-" + expanseBudgetArr[i][1] + "</option>"
                    }

                }

            }
        }
        dropDown += "</select>"
        return dropDown
    }


    def getGLAccountExpanseBudgetForReconcilationDropDown(returnIndex, budgetId) {

        // def AllBudgetAccountArr = new BudgetViewDatabaseService().executeQueryAtSingle("SELECT gl_account FROM budget_item_expense_details WHERE budget_item_expense_id='$budgetId' GROUP BY gl_account")
        def AllBudgetAccountArr = new BudgetViewDatabaseService().executeQueryAtSingle("SELECT b.gl_account AS budgetAccount FROM budget_item_expense AS a LEFT JOIN budget_item_expense_details AS b ON a.id=b.budget_item_expense_id WHERE a.vendor_id='$budgetId' GROUP BY b.gl_account ORDER BY b.gl_account ")

        String dropDown = "<select name='" + returnIndex + "'>"
        if (AllBudgetAccountArr.size()) {
            def tempAccount = AllBudgetAccountArr.join(",")
            def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT a.id,a.name AS categoryName FROM chart_group AS a LEFT JOIN chart_master AS b ON a.id=b.chart_group_id WHERE a.status=1 AND b.account_code IN($tempAccount) GROUP BY a.id")


            if (CatArr.size()) {
                for (int i = 0; i < CatArr.size(); i++) {
                    def catId = CatArr[i][0]
                    def ProductArr
                    ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,accountCode,accountName FROM bv.ChartMaster where status='1' AND chart_group_id='" + catId + "' AND account_code IN($tempAccount)")
                    if (ProductArr.size()) {
                        dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                        for (int j = 0; j < ProductArr.size(); j++) {

                            dropDown += "<option value='" + ProductArr[j][1] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        }
                        dropDown += "</optgroup>"
                    }
                }
            }


        }
        dropDown += "</select>"
        return dropDown
    }


    def getAjaxGLAccountExpanseBudgetForReconcilationDropDown(returnIndex, selectValName, contextCustomer = "", budgetId, selectedByBankAccountNo = "") {

        def AllBudgetAccountArr = new BudgetViewDatabaseService().executeQueryAtSingle("SELECT b.gl_account AS budgetAccount FROM budget_item_expense AS a LEFT JOIN budget_item_expense_details AS b ON a.id=b.budget_item_expense_id WHERE a.vendor_id='$budgetId' GROUP BY b.gl_account ORDER BY b.gl_account ")

        String dropDown = "<select id=\"${returnIndex}\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BankReconciliation/budgetCodeWiseExpanseGLCode',success:function(data,textStatus){jQuery('#ajaxbudgetCodeWiseExpanseGLCodeDiv').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"${returnIndex}\" style=\"\">"

        if (AllBudgetAccountArr.size()) {
            def tempAccount = AllBudgetAccountArr.join(",")
            def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT a.id,a.name AS categoryName FROM chart_group AS a LEFT JOIN chart_master AS b ON a.id=b.chart_group_id WHERE a.status=1 AND b.account_code IN($tempAccount) GROUP BY a.id")

            if (CatArr.size()) {
                for (int i = 0; i < CatArr.size(); i++) {
                    def catId = CatArr[i][0]
                    def ProductArr
                    ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,accountCode,accountName FROM bv.ChartMaster where status='1' AND chart_group_id='" + catId + "' AND account_code IN($tempAccount)")
                    if (ProductArr.size()) {
                        dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                        for (int j = 0; j < ProductArr.size(); j++) {

                            if (selectValName == ProductArr[j][1]) {

                                if (selectedByBankAccountNo.trim()) {
                                    dropDown += "<option value='" + ProductArr[j][1] + "::" + selectedByBankAccountNo + "::" + budgetId + "' selected>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                                } else {
                                    dropDown += "<option value='" + ProductArr[j][1] + "' selected>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                                }

                            } else {
                                if (selectedByBankAccountNo.trim()) {
                                    dropDown += "<option value='" + ProductArr[j][1] + "::" + selectedByBankAccountNo + "::" + budgetId + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                                } else {
                                    dropDown += "<option value='" + ProductArr[j][1] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                                }
                            }
                        }
                        dropDown += "</optgroup>"
                    }
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }


    def getGLAccountExpanseBudgetForReconcilationOthersDropDown(returnIndex, def selectGlAcc = "") {

        def g = new ValidationTagLib()
        String dropDown = "<select name='" + returnIndex + "' required >"
        def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT c.id,c.name AS categoryName FROM chart_class_type AS a LEFT JOIN chart_class AS b ON a.id=b.chart_class_type_id LEFT JOIN chart_group AS c ON b.id=c.chart_class_id WHERE c.status=1 AND a.id IN(1,2,7)")
        if (CatArr.size()) {
            for (int i = 0; i < CatArr.size(); i++) {
                def catId = CatArr[i][0]
                def ProductArr
                ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,accountCode,accountName FROM bv.ChartMaster where status='1' AND chart_group_id='" + catId + "'")
                dropDown += "<option value='' >" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
                if (ProductArr.size()) {
                    dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                    for (int j = 0; j < ProductArr.size(); j++) {
                        if (selectGlAcc == ProductArr[j][1]) {
                            dropDown += "<option value='" + ProductArr[j][1] + "' selected='selected'>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        } else {
                            dropDown += "<option value='" + ProductArr[j][1] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        }
                    }
                    dropDown += "</optgroup>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getVendorDropDownListForReconciliationWithoutAjax(returnIndex, selectIndex = '0', contextCustomer = "", vendorType = "", vendorId = "") {

        //def vendorArr = new BudgetViewDatabaseService().executeQuery("SELECT vm.id,vm.vendorName,vm.vendorCode FROM InvoiceExpense AS budget_exp, VendorMaster As vm  where budget_exp.status=1 AND budget_exp.isReverse=0 AND budget_exp.vendorId=vm.id "+ (vendorType!="" ? "AND vendor_type = '"+vendorType+"'" : "") +"GROUP BY budget_exp.vendorId")
        //def vendorQuery = "SELECT vm.id,vm.vendorName,vm.vendorCode FROM VendorMaster As vm  where vendor_type = '"+ vendorType +"' ORDER BY vm.id";
        int venId
        if (vendorId != null) {
              venId = Integer.parseInt(vendorId)
        }

        def vendorQuery = "SELECT vm.id,vm.vendorName,vm.vendorCode FROM VendorMaster As vm where vendor_type = 'vn' ORDER BY vm.vendorName";

        def vendorArr = new BudgetViewDatabaseService().executeQuery(vendorQuery)
        String dropDown = "<select required=\"\" name='" + returnIndex + "'>"
        def vendorPrefix = showGeneratedVendorCode();
        def g = new ValidationTagLib()

        if (vendorArr.size()) {
            dropDown += "<option value='0' >" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
            for (int i = 0; i < vendorArr.size(); i++) {
                if (venId == vendorArr[i][0]){
                    dropDown += "<option value='" + vendorArr[i][0] + "' selected='selected'>" + vendorArr[i][1] + " [" + vendorPrefix + "-" + vendorArr[i][2] + "]" + "</option>"
                } else if (vendorArr[i][0] == Integer.parseInt(selectIndex)) {
                    dropDown += "<option value='" + vendorArr[i][0] + "' selected='selected'>" + vendorArr[i][1] + " [" + vendorPrefix + "-" + vendorArr[i][2] + "]" + "</option>"
               }  else {
                   dropDown += "<option value='" + vendorArr[i][0] + "' >" + vendorArr[i][1] + " [" + vendorPrefix + "-" + vendorArr[i][2] + "]" + "</option>"
                }
            }
        }
        dropDown += "</select>"

        return dropDown
    }


    def getInvoiceTypeForReconciliation(returnIndex, selectIndex = '0', contextCustomer = "", relationalBankAccount) {

        String dropDown = "<select id=\"${returnIndex}\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BankReconciliation/selectInvoiceTypeForTwoForm',success:function(data,textStatus){jQuery('#divInvoiceTypeForTwoForm').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"${returnIndex}\" style=\"width:120px;\">"
        dropDown += "<option value='ExpanseBudget::${relationalBankAccount}' >Expanse Budget</option>"
        dropDown += "<option value='IncomeBudget::${relationalBankAccount}' >Income Budget</option>"
        dropDown += "</select>"
        return dropDown
    }

    def getCustomerDropDownListForReconciliationWithoutAjax(returnIndex, selectIndex = '0', contextCustomer = "") {

        def vendorArr = new BudgetViewDatabaseService().executeQuery("SELECT vm.id,vm.customer_name,vm.customer_code FROM invoice_income AS budget_exp, customer_master AS vm  WHERE budget_exp.status=1 AND budget_exp.is_reverse=0 AND budget_exp.customer_id=vm.id GROUP BY budget_exp.customer_id")
        String dropDown = "<select name='" + returnIndex + "'>"
        def vendorPrefix = showGeneratedCustomerCode();
        def g = new ValidationTagLib()
        if (vendorArr.size()) {
            dropDown += "<option value='0' >" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
            for (int i = 0; i < vendorArr.size(); i++) {
                if (vendorArr[i][0] == Integer.parseInt(selectIndex)) {
                    dropDown += "<option value='" + vendorArr[i][0] + "' selected='selected'>" + vendorArr[i][1] + " [" + vendorPrefix + "-" + vendorArr[i][2] + "]" + "</option>"
                } else {
                    dropDown += "<option value='" + vendorArr[i][0] + "' >" + vendorArr[i][1] + " [" + vendorPrefix + "-" + vendorArr[i][2] + "]" + "</option>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getIncomeBudgetForReconcilationDown(returnIndex, selectValName, contextCustomer = "") {

        ///////////ALL CUSTOMER///////////////////
        def ActiveFiscalYear = new CoreParamsHelper().getActiveFiscalYear()
        def FiscalYearInfo = new CoreParamsHelper().getActiveFiscalYearInformation(ActiveFiscalYear)

        LinkedHashMap gridResultVendorArr
        String select = "DISTINCT(a.customerId) AS customer_id,v.customerName As customer_name"
        String selectIndex = "customer_id,customer_name"
        String from = "BudgetItemIncome AS a,CustomerMaster AS v"
        String where = "a.bookingPeriodStartMonth >='" + FiscalYearInfo[0][3] + "' AND a.bookingPeriodStartYear ='" + FiscalYearInfo[0][4] + "' AND a.bookingPeriodStartMonth <='" + FiscalYearInfo[0][5] + "' AND a.bookingPeriodStartYear='" + FiscalYearInfo[0][6] + "' AND  a.customerId=v.id AND a.customerId!='' AND a.status=1"
        String orderBy = ""
        gridResultVendorArr = new BudgetViewDatabaseService().select(select, from, where, orderBy, '', 'true', selectIndex)

        def expanseBudgetArr = gridResultVendorArr['dataGridList']

        //String dropDown = "<select name='" + returnIndex + "' >"
        String dropDown = "<select id=\"${returnIndex}\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BankReconciliation/budgetCodeWiseIncomeInvoice',success:function(data,textStatus){jQuery('#ajaxbudgetCodeWiseExpanseInvoiceDiv').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"${returnIndex}\" style=\" width: 182px;\">"

        if (expanseBudgetArr.size()) {
            int tem = 0
            for (int i = 0; i < expanseBudgetArr.size(); i++) {
                if (selectValName == expanseBudgetArr[i][0]) {
                    dropDown += "<option value='" + expanseBudgetArr[i][0] + "' selected>" + expanseBudgetArr[i][1] + "</option>"
                } else {
                    dropDown += "<option value='" + expanseBudgetArr[i][0] + "' >" + expanseBudgetArr[i][1] + "</option>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getGLAccountIncomeBudgetForReconcilationDropDown(returnIndex, budgetId) {

        def AllBudgetAccountArr = new BudgetViewDatabaseService().executeQueryAtSingle("SELECT b.gl_account AS budgetAccount FROM budget_item_income AS a LEFT JOIN budget_item_income_details AS b ON a.id=b.budget_item_income_id WHERE a.customer_id='$budgetId' GROUP BY b.gl_account ORDER BY b.gl_account ")

        String dropDown = "<select name='" + returnIndex + "'>"
        if (AllBudgetAccountArr.size()) {
            def tempAccount = AllBudgetAccountArr.join(",")
            def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT a.id,a.name AS categoryName FROM chart_group AS a LEFT JOIN chart_master AS b ON a.id=b.chart_group_id WHERE a.status=1 AND b.account_code IN($tempAccount) GROUP BY a.id")
            if (CatArr.size()) {
                for (int i = 0; i < CatArr.size(); i++) {
                    def catId = CatArr[i][0]
                    def ProductArr
                    ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,accountCode,accountName FROM bv.ChartMaster where status='1' AND chart_group_id='" + catId + "' AND account_code IN($tempAccount)")
                    if (ProductArr.size()) {
                        dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                        for (int j = 0; j < ProductArr.size(); j++) {

                            dropDown += "<option value='" + ProductArr[j][1] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                        }
                        dropDown += "</optgroup>"
                    }
                }
            }


        }
        dropDown += "</select>"
        return dropDown
    }

    def getManualReconciliationRelationalBankAccountDropDown(returnIndex, selectIndex, contextCustomer = "", actionTypeId = 0, reconcilType = "", tc = "") {


        def g = new ValidationTagLib()

        ArrayList allBankAccountArr = new ArrayList()

        def all_bankAccountString = """SELECT b.by_bank_account_no,vm.vendor_name,cm.customer_name,pm.budget_name FROM bank_statement_import_final AS a
                                                        LEFT JOIN bank_statement_import_details_final AS b ON a.id=b.bank_import_id
                                                        LEFT JOIN vendor_bank_account AS vba ON b.by_bank_account_no=vba.bank_account_no
                                                        LEFT JOIN vendor_master AS vm ON vba.vendor_id=vm.id
                                                        LEFT JOIN customer_bank_account AS cba ON b.by_bank_account_no=cba.bank_account_no
                                                        LEFT JOIN customer_master AS cm ON cba.customer_id = cm.id
                                                        LEFT JOIN private_budget_bank_account AS pba ON b.by_bank_account_no = pba.bank_account_no
                                                        LEFT JOIN private_budget_master AS pm ON pba.budget_master_id = pm.id
                                                        LEFT JOIN reservation_budget_bank_account AS rba ON b.by_bank_account_no = rba.bank_account_no
                                                        LEFT JOIN reservation_budget_master AS rm ON rba.budget_master_id = rm.id
                                                        WHERE b.skip_account != 2 AND b.reconcilated !=1 and ROUND((b.amount - b.reconciliated_amount),2) != 0
                                                        GROUP BY b.by_bank_account_no, b.bank_import_id"""

        def allBankAccountArrManual = new BudgetViewDatabaseService().executeQuery(all_bankAccountString)

        if (allBankAccountArrManual.size()) {
            allBankAccountArrManual.each { phn ->
                allBankAccountArr << phn
            }
        }

        def companyBankAccountWithConditionString = "SELECT bidf.by_bank_account_no,vm.vendor_name,cm.customer_name " +
                "FROM company_bank_accounts AS cmba,bank_statement_import_details_final AS bidf " +
                "LEFT JOIN vendor_bank_account AS vba ON bidf.by_bank_account_no=vba.bank_account_no " +
                "LEFT JOIN vendor_master AS vm ON vba.vendor_id=vm.id " +
                "LEFT JOIN customer_bank_account AS cba ON bidf.by_bank_account_no=cba.bank_account_no " +
                "LEFT JOIN customer_master AS cm ON cba.customer_id=cm.id " +
                //"WHERE (ROUND((bidf.amount - bidf.reconciliated_amount),2) != 0 AND bidf.skip_account != 2) " +
                "WHERE (ROUND((bidf.amount - bidf.reconciliated_amount),2) != 0 AND bidf.skip_account != 2 AND bidf.by_bank_account_no = cmba.bank_account_no) " +
                "GROUP BY bidf.by_bank_account_no";

        def companyBankAccountWithCondition = new BudgetViewDatabaseService().executeQuery(companyBankAccountWithConditionString)

        if (companyBankAccountWithCondition.size()) {
            companyBankAccountWithCondition.each { phn ->

                if (!allBankAccountArr.contains(phn)) {
                    allBankAccountArr << phn
                }
            }
        }

        String dropDown = "";
        if (reconcilType.equals("ManualReconcilRelationalBankAccount")) {
            dropDown = "<select id=\"${returnIndex}\" onchange=\"onChangeRelationBankNo(this.value, 'ManualReconciliationRelationalBankAccount');\" name=\"${returnIndex}\" style=\"\">"
//            dropDown = "<select id=\"${returnIndex}\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BankReconciliation/ManualReconciliationRelationalBankAccount',success:function(data,textStatus){jQuery('#updateReconciliationInfoArea').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"${returnIndex}\" style=\"\">"
        } else if (reconcilType.equals("BookedInvoiceReceipt")) {
            dropDown = "<select id=\"${returnIndex}\" onchange=\"onChangeRelationBankNo(this.value, 'reconciliationRelationalBankBookedInvoiecReceiptPayment');\" name=\"${returnIndex}\" style=\"\">"
//            dropDown = "<select id=\"${returnIndex}\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BankReconciliation/reconciliationRelationalBankBookedInvoiecReceiptPayment',success:function(data,textStatus){jQuery('#updateReconciliationInfoArea').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"${returnIndex}\" style=\"\">"
        } else if (reconcilType.equals("WithoutInvoice")) {
            dropDown = "<select id=\"${returnIndex}\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BankReconciliation/ReconciliationRelationalBankAccountWithoutInvoice',success:function(data,textStatus){jQuery('#updateReconciliationInfoArea').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"${returnIndex}\" style=\"\">"
        } else if (reconcilType.equals("NotYetBooked")) {
            dropDown = "<select id=\"${returnIndex}\" onchange=\"onChangeRelationBankNo(this.value, 'ReconciliationRelationalBankAccountNotYetBooked');\" name=\"${returnIndex}\" style=\"\">"
//            dropDown = "<select id=\"${returnIndex}\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BankReconciliation/ReconciliationRelationalBankAccountNotYetBooked',success:function(data,textStatus){jQuery('#updateReconciliationInfoArea').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"${returnIndex}\" style=\"\">"
        } else if (reconcilType.equals("Other")) {
            dropDown = "<select id=\"${returnIndex}\" onchange=\"onChangeRelationBankNo(this.value, 'ReconciliationRelationalBankAccountOthers');\" name=\"${returnIndex}\" style=\"\">"
//            dropDown = "<select id=\"${returnIndex}\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BankReconciliation/ReconciliationRelationalBankAccountOthers',success:function(data,textStatus){jQuery('#updateReconciliationInfoArea').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"${returnIndex}\" style=\"\">"
        } else if (reconcilType.equals("RelationalBankAccount")) {
            dropDown = "<select id=\"${returnIndex}\" onchange=\"onChangeRelationBankNo(this.value, 'ReconciliationRelationalBankAccount');\" name=\"${returnIndex}\" style=\"\">"
//            dropDown = "<select id=\"${returnIndex}\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BankReconciliation/ReconciliationRelationalBankAccount',success:function(data,textStatus){jQuery('#updateReconciliationInfoArea').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"${returnIndex}\" style=\"\">"
        } else if (reconcilType.equals("PrivateSpending")) {
            dropDown = "<select id=\"${returnIndex}\" onchange=\"onChangeRelationBankNo(this.value, 'ReconciliationRelationalPrivateSpending');\" name=\"${returnIndex}\" style=\"\">"
//            dropDown = "<select id=\"${returnIndex}\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BankReconciliation/ReconciliationRelationalPrivateSpending',success:function(data,textStatus){jQuery('#updateReconciliationInfoArea').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"${returnIndex}\" style=\"\">"
        } else if (reconcilType.equals("ReservationSpending")) {
            dropDown = "<select id=\"${returnIndex}\" onchange=\"onChangeRelationBankNo(this.value, 'ReconciliationRelationalReservationSpending');\" name=\"${returnIndex}\" style=\"\">"
//            dropDown = "<select id=\"${returnIndex}\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BankReconciliation/ReconciliationRelationalReservationSpending',success:function(data,textStatus){jQuery('#updateReconciliationInfoArea').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"${returnIndex}\" style=\"\">"
        }
        //dropDown += "<option value='0" + "::" + actionTypeId + "'>Select Bank Account</option>"

        if (allBankAccountArr.size()) {
            for (int i = 0; i < allBankAccountArr.size(); i++) {
                def transType= new ReconciliationService().matchingInfoOfVendorOrCustomer(allBankAccountArr[i][0])
                def bankAcountNoWithVenOrCusName = ""

                if (transType.cusOrVen == 'CUS') {
                    def cusName = (allBankAccountArr[i][2] == null) ? "" : " - " + allBankAccountArr[i][2];
                    bankAcountNoWithVenOrCusName = allBankAccountArr[i][0] +cusName;
                } else if (transType.cusOrVen == "VEN") {
                    def venName = (allBankAccountArr[i][1] == null) ? "" : " - " + allBankAccountArr[i][1];
                    bankAcountNoWithVenOrCusName = allBankAccountArr[i][0] +venName;
                }else if (transType.cusOrVen == 'PVT') {
                    def privateName = (allBankAccountArr[i][3] == null) ? "" : " - " + allBankAccountArr[i][3];
                    bankAcountNoWithVenOrCusName = allBankAccountArr[i][0] +privateName;
                }

                /* def cusName = (allBankAccountArr[i][2] == null) ? "" : " - " + allBankAccountArr[i][2];
                 def venName = (allBankAccountArr[i][1] == null) ? "" : " - " + allBankAccountArr[i][1];
                 def privateName = (allBankAccountArr[i][3] == null) ? "" : " - " + allBankAccountArr[i][3];
                 def bankAcountNoWithVenOrCusName = ""*/
                def bankAcountNo = ""
                if (allBankAccountArr[i][0] == "") {
                    allBankAccountArr.remove(i)
                } else {
                    bankAcountNo = allBankAccountArr[i][0]
//                    bankAcountNoWithVenOrCusName = allBankAccountArr[i][0] + cusName + venName + privateName;

                    if (bankAcountNo == selectIndex) {
                        dropDown += "<option value='" + bankAcountNo + "::" + actionTypeId + "::" + tc + "' selected='selected'>" + bankAcountNoWithVenOrCusName + "</option>"
                    } else {
                        dropDown += "<option value='" + bankAcountNo + "::" + actionTypeId + "::" + tc + "'>" + bankAcountNoWithVenOrCusName + "</option>"
                    }
                }
            }
        } else {
            dropDown += "<option>" + g.message(code: 'coreParamsHelper.noRelationalBankAccount.label') + "</option>"
            //No Relational bank account
//            dropDown += "<option >"+g.message(code:'coreParamsHelper.noRelationalBankAccount.label')+"</option>" //No Relational bank account

        }

        dropDown += "</select>"
        return dropDown
    }

    def getPaymentTypeFromBankAccountNo(bankAccountNo) {

        def paymentType = "0"
        def checkVendorCustomerAccount = "SELECT b.by_bank_account_no,ROUND((b.amount - b.reconciliated_amount),2)  FROM bank_statement_import_final AS a  " +
                " LEFT JOIN bank_statement_import_details_final AS b ON a.id=b.bank_import_id " +
                " LEFT JOIN vendor_bank_account AS vba ON b.by_bank_account_no=vba.bank_account_no " +
                " LEFT JOIN customer_bank_account AS cba ON b.by_bank_account_no=cba.bank_account_no " +
                " WHERE ROUND((b.amount - b.reconciliated_amount),2) != 0 AND " +
                " (vba.bank_account_no ='" + bankAccountNo + "'|| cba.bank_account_no = '" + bankAccountNo + "')"
        def resultAccountType = new BudgetViewDatabaseService().executeQuery(checkVendorCustomerAccount)
        if (resultAccountType.size() == 0) {

            def checkPrivateAccount = "SELECT b.by_bank_account_no,ROUND((b.amount - b.reconciliated_amount),2)  FROM bank_statement_import_final AS a " +
                    " LEFT JOIN bank_statement_import_details_final AS b ON a.id=b.bank_import_id " +
                    " LEFT JOIN vendor_bank_account AS vba ON b.by_bank_account_no=vba.bank_account_no  " +
                    " LEFT JOIN customer_bank_account AS cba ON b.by_bank_account_no=cba.bank_account_no " +
                    " WHERE  (vba.bank_account_no = '" + bankAccountNo + "' || cba.bank_account_no ='" + bankAccountNo + "' ) "
            def resultPrivateAccount = new BudgetViewDatabaseService().executeQuery(checkPrivateAccount)
            if (resultPrivateAccount.size() == 0) {

                def checkReservationAccount = "SELECT b.by_bank_account_no,ROUND((b.amount - b.reconciliated_amount),2)  FROM bank_statement_import_final AS a " +
                        " LEFT JOIN bank_statement_import_details_final AS b ON a.id=b.bank_import_id " +
                        " LEFT JOIN reservation_budget_bank_account AS rba ON b.by_bank_account_no=rba.bank_account_no " +
                        " WHERE rba.bank_account_no = '" + bankAccountNo + "' "
                def resultReservationAccount = new BudgetViewDatabaseService().executeQuery(checkReservationAccount)

                if (resultReservationAccount.size() == 0) {
                    paymentType = "0"
                } else {
                    paymentType = "6"
                }

            } else {

                paymentType = "5"
            }

        } else {
            paymentType = "1"
        }

        return paymentType
    }

    def updateBankPaymentIdAtImportDetailsFinal(Index, ReconciliationIndex) {
        def updatedBankPaymentId = ""
        updatedBankPaymentId = ReconciliationIndex
        new BudgetViewDatabaseService().executeUpdate("UPDATE bank_statement_import_details_final SET " +
                "bank_payment_id='" + updatedBankPaymentId + "' WHERE id=" + Index)
        return true
    }

    def resetSkipStatus() {
        String strQuery = """ UPDATE bank_statement_import_details_final SET skip_account = 0 """
        new BudgetViewDatabaseService().executeUpdate(strQuery)

        return true
    }

    def getAllBankAccountForManualReconciliation() {

        ArrayList allBankAccountArrx = new ArrayList()

        def all_bankAccountStringAutox = "SELECT b.by_bank_account_no FROM bank_statement_import_final AS a " +
                "LEFT JOIN bank_statement_import_details_final AS b ON a.id=b.bank_import_id " +
                "WHERE b.skip_account != 2 AND b.reconcilated!=1 " +
                "AND by_bank_account_no!='' GROUP BY b.by_bank_account_no";
//        GROUP BY b.by_bank_account_no
        def allBankAccountArrAutox = new BudgetViewDatabaseService().executeQuery(all_bankAccountStringAutox)

        //allBankAccountArrx << '0'
        if (allBankAccountArrAutox.size()) {
            allBankAccountArrAutox.each { phnxx ->
                if (!allBankAccountArrx.contains(phnxx)) {
                    allBankAccountArrx << phnxx
                }
            }
        }

        return allBankAccountArrx;
    }

    def getAllAccountTransForBothDebitCredit(def selectedByBankAccountNo) {

        ArrayList allBankAccountArr = new ArrayList()

        def all_bankAccountStringAuto = "SELECT b.* FROM bank_statement_import_final AS a " +
                "LEFT JOIN bank_statement_import_details_final AS b ON a.id = b.bank_import_id " +
                "WHERE  ROUND((b.amount - b.reconciliated_amount),2) != 0  AND b.skip_account != 2 " +
                " AND b.by_bank_account_no='${selectedByBankAccountNo}' ORDER BY b.id DESC";
        def bac = all_bankAccountStringAuto[11];

//        all_bankAccountStringAuto = all_bankAccountStringAuto + (debitCredit != '' ? " AND debit_credit='${debitCredit}'" : "");
        def allBankAccountArrAuto = new BudgetViewDatabaseService().executeQueryWithoutCamelCase(all_bankAccountStringAuto)

        if (allBankAccountArrAuto.size()) {
            allBankAccountArrAuto.each { phnx ->
                allBankAccountArr << phnx
            }
        }

        return allBankAccountArr;
    }

    def getAllBankAccountForManualReconciliationBySelectedBankAccount(
            def selectedByBankAccountNo, def debOrCredit = "") {

        ArrayList allBankAccountArr = new ArrayList()

        def all_bankAccountStringAuto = "SELECT b.* FROM bank_statement_import_final AS a " +
                "LEFT JOIN bank_statement_import_details_final AS b ON a.id = b.bank_import_id " +
                "WHERE  ROUND((b.amount - b.reconciliated_amount),2) != 0  AND b.skip_account != 2 " +
                " AND b.by_bank_account_no='${selectedByBankAccountNo}' AND debit_credit='${debOrCredit}' ORDER BY b.id DESC";
        def bac = all_bankAccountStringAuto[11];

//        all_bankAccountStringAuto = all_bankAccountStringAuto + (debitCredit != '' ? " AND debit_credit='${debitCredit}'" : "");
        def allBankAccountArrAuto = new BudgetViewDatabaseService().executeQuery(all_bankAccountStringAuto)

        if (allBankAccountArrAuto.size()) {
            allBankAccountArrAuto.each { phnx ->
                allBankAccountArr << phnx
            }
        }

        return allBankAccountArr;
    }

    def getAccountNoForAllDebitCreditTransForBookedInvoiecReceipt(def selectedByBankAccountNo) {

        ArrayList allBankAccountArr = new ArrayList()

        def all_bankAccountStringAuto = """SELECT b.* FROM bank_statement_import_final AS a 
                LEFT JOIN bank_statement_import_details_final AS b ON a.id = b.bank_import_id 
                WHERE  ROUND((b.amount - b.reconciliated_amount),2) != 0  AND b.skip_account != 2 
                AND b.by_bank_account_no= '${selectedByBankAccountNo}' ORDER BY b.trans_date_time ASC"""


        def allBankAccountArrAuto = new BudgetViewDatabaseService().executeQueryWithoutCamelCase(all_bankAccountStringAuto)

        if (allBankAccountArrAuto.size()) {
            allBankAccountArrAuto.each { phnx ->
                allBankAccountArr << phnx
            }
        }

        return allBankAccountArr;
    }

    def getAccountNoForAllDebitCreditTrans(def selectedByBankAccountNo) {

        ArrayList allBankAccountArr = new ArrayList()


        def all_bankAccountStringAuto = "SELECT b.* FROM bank_statement_import_final AS a " +
                "LEFT JOIN bank_statement_import_details_final AS b ON a.id = b.bank_import_id " +
                "WHERE  ROUND((b.amount - b.reconciliated_amount),2) != 0  AND b.skip_account != 2 " +
                " AND b.by_bank_account_no='${selectedByBankAccountNo}' ORDER BY b.trans_date_time ASC";

        def bac = all_bankAccountStringAuto[11];

//        all_bankAccountStringAuto = all_bankAccountStringAuto + (debitCredit != '' ? " AND debit_credit='${debitCredit}'" : "");
        def allBankAccountArrAuto = new BudgetViewDatabaseService().executeQueryWithoutCamelCase(all_bankAccountStringAuto)
//        def allBankAccountArrAuto = new BudgetViewDatabaseService().executeQuery(all_bankAccountStringAuto)

        if (allBankAccountArrAuto.size()) {
            allBankAccountArrAuto.each { phnx ->
                allBankAccountArr << phnx
            }
        }

        return allBankAccountArr;
    }

    def getGeneratedChartCode(subGLAccountCode) {

        def selectedGLAccountInfoSql = "SELECT * FROM chart_master WHERE account_code='${subGLAccountCode}' ";

        def selectedGLAccountInfo = new BudgetViewDatabaseService().executeQuery(selectedGLAccountInfoSql)

        //Integer startIndex = Integer.parseInt(subGLAccountCode.toString())
        Integer startIndex = Integer.parseInt(subGLAccountCode.toString())
        Map map = ["accountCode": 0, "chartGroupid": 0]
        Integer i = 0
        for (i = startIndex + 1; i <= startIndex + 99; i++) {

            def selectedGLAccountInfoSqlTemp = "SELECT * FROM chart_master WHERE account_code='${i}' ";
            def selectedGLAccountInfoTemp = new BudgetViewDatabaseService().executeQuery(selectedGLAccountInfoSqlTemp)
            if (selectedGLAccountInfoTemp.size() == 0) {
                map.accountCode = i
                map.chartGroupid = selectedGLAccountInfo[0][5]
                break
            }
        }
        return map
    }

    def getBudgetItemExpenseDetailsIdFromInvoiceExpense(invoiceExpenseId) {

        def selectedGLAccountInfoSql = "SELECT b.id FROM invoice_expense As a LEFT JOIN budget_item_expense_details As b ON a.budget_item_expense_id=b.budget_item_expense_id WHERE a.id='${invoiceExpenseId}' ORDER BY b.id DESC ";
        def selectedGLAccountInfo = new BudgetViewDatabaseService().executeQuery(selectedGLAccountInfoSql)
        return selectedGLAccountInfo[0][0]

    }

    def getBudgetItemIncomeDetailsIdFromInvoiceIncome(invoiceIncomeId) {

        def selectedGLAccountInfoSql = "SELECT b.id FROM invoice_income As a LEFT JOIN budget_item_income_details As b ON a.budget_item_income_id=b.budget_item_income_id WHERE a.id='${invoiceIncomeId}' ORDER BY b.id DESC ";
        def selectedGLAccountInfo = new BudgetViewDatabaseService().executeQuery(selectedGLAccountInfoSql)
        return selectedGLAccountInfo[0][0]
    }

    def hasBankPaymentIdImportDetailsTable(importId) {
        def bankImportIdArr = new BudgetViewDatabaseService().executeQuery("SELECT bankPaymentId FROM bankStatementImportDetailsFinal WHERE id='${importId}'")
        def bankImportIdString = bankImportIdArr[0][0]
        Integer bankImportId = 0
        if (bankImportIdString) {
            bankImportId = Integer.parseInt(bankImportIdString.toString())
        }
        return bankImportId
    }

    def undoReconcliationProcess(bankId) {

//        def transMasterArr = new BudgetViewDatabaseService().executeQuery("SELECT ABS(amount),recenciliationCode FROM transMaster WHERE invoiceNo='${bankId}' AND trans_type=7 GROUP BY recenciliationCode ")
        def transMasterArr = new BudgetViewDatabaseService().executeQuery("SELECT ABS(amount),recenciliationCode,trans_type FROM transMaster WHERE invoiceNo='${bankId}' AND (trans_type=7 OR trans_type=10 OR trans_type=6 ) GROUP BY recenciliationCode ")
//        def transMasterArr = new BudgetViewDatabaseService().executeQuery("SELECT ABS(amount),recenciliationCode,trans_type FROM transMaster WHERE invoiceNo='${bankId}' AND (trans_type=7 OR trans_type=10 ) GROUP BY recenciliationCode ")

        if (transMasterArr.size()) {
            transMasterArr.each { phnx ->
                def amount = phnx[0]
                def recenciliationCode = phnx[1]
                def transType = phnx[2]

                if (recenciliationCode.trim()) {
                    def recenciliationCodeArr = recenciliationCode.trim().split("#")
                    def invoiceNo = recenciliationCodeArr[0]
                    def invoiceType = recenciliationCodeArr[1]

                    if (Integer.parseInt(invoiceType.toString()) == 1) {
                        /////Income///
                        undoIncomeInvoiceInformation(invoiceNo, amount)
                    } else if (Integer.parseInt(invoiceType.toString()) == 2) {
                        ///Expense////
                        undoExpanseInvoiceInformation(invoiceNo, amount)
                    }

                    undoBankTransInformation(bankId, amount)

                    if (transType == 7) {
                        undoCompanyBankTrans(bankId)
                        undoTransMaster(bankId, amount, recenciliationCode)
                    } else if (transType == 6) {
                        undoCompanyBankTransType6(bankId)
                        undoTransMasterTransType6(bankId, amount, recenciliationCode)
                        undoPrivateReservationSpendingTransType6(bankId)
                    } else if (transType == 10) {
                        undoCompanyBankTransForPrivate(bankId)
                        undoTransMasterForPrivateWithdrawls(bankId, amount, recenciliationCode)
                    }

                } else {
                    undoBankTransInformation(bankId, amount)
                    if (transType == 7) {
                        undoCompanyBankTrans(bankId)
                        undoTransMaster(bankId, amount, recenciliationCode)
                    } else if (transType == 6) {
                        undoCompanyBankTransType6(bankId)
                        undoTransMasterTransType6(bankId, amount, recenciliationCode)
                        undoPrivateReservationSpendingTransType6(bankId)
                    } else if (transType == 10) {
                        undoCompanyBankTransForPrivate(bankId)
                        undoTransMasterForPrivateWithdrawls(bankId, amount, recenciliationCode)
                    }
                }
            }
        }
    }

    def undoBankTransInformation(bankId, amount) {
        String updateQuery = "UPDATE bank_statement_import_details_final SET remain_amount = amount, reconciliated_amount=0,reconcilated=0,skip_account=0 WHERE bank_payment_id =" + bankId
        new BudgetViewDatabaseService().executeUpdate(updateQuery)
    }

    def undoCompanyBankTrans(bankId) {
        new BudgetViewDatabaseService().executeUpdate("DELETE FROM  company_bank_trans WHERE trans_type=7 AND invoice_no=" + bankId)
    }

    def undoCompanyBankTransType6(bankId) {
        new BudgetViewDatabaseService().executeUpdate("DELETE FROM  company_bank_trans WHERE trans_type=6 AND invoice_no=" + bankId)
    }

    def undoPrivateReservationSpendingTransType6(bankId) {
        new BudgetViewDatabaseService().executeUpdate("DELETE FROM  private_reservation_spending_trans WHERE trans_type=6 AND bank_payment_id=" + bankId)
    }

    def undoCompanyBankTransForPrivate(bankId) {
        new BudgetViewDatabaseService().executeUpdate("DELETE FROM  company_bank_trans WHERE trans_type=10 AND invoice_no=" + bankId)
    }

    def undoTransMaster(bankId, amount, recenciliationCode) {
        new BudgetViewDatabaseService().executeUpdate("DELETE FROM  trans_master WHERE trans_type=7 AND ABS(amount)='" + amount + "' AND recenciliation_code='" + recenciliationCode + "' AND invoice_no='" + bankId + "'")
    }

    def undoTransMasterTransType6(bankId, amount, recenciliationCode) {
        new BudgetViewDatabaseService().executeUpdate("DELETE FROM  trans_master WHERE trans_type=6 AND ABS(amount)='" + amount + "' AND recenciliation_code='" + recenciliationCode + "' AND invoice_no='" + bankId + "'")
    }

    def undoTransMasterForPrivateWithdrawls(bankId, amount, recenciliationCode) {
        new BudgetViewDatabaseService().executeUpdate("DELETE FROM  trans_master WHERE trans_type=10 AND ABS(amount)='" + amount + "' AND recenciliation_code='" + recenciliationCode + "' AND invoice_no='" + bankId + "'")
    }

    def undoIncomeInvoiceInformation(invoiceNo, amount) {
        new BudgetViewDatabaseService().executeUpdate("UPDATE invoice_income SET paid_amount=paid_amount-'" + amount + "' WHERE id=" + invoiceNo)

        def selectedReconcilatedStatusSql = "SELECT CASE WHEN b.paid_amount<=0 THEN 0 WHEN b.paid_amount<(b.total_gl_amount+total_vat) THEN 2 ELSE 1 END AS paid_status FROM invoice_income AS b WHERE b.id='${invoiceNo}' ";
        def selectedReconcilatedStatusArr = new BudgetViewDatabaseService().executeQuery(selectedReconcilatedStatusSql)
        def selectedReconcilatedStatus = selectedReconcilatedStatusArr[0][0]
        new BudgetViewDatabaseService().executeUpdate("UPDATE invoice_income SET paid_status='" + selectedReconcilatedStatus + "' WHERE id=" + invoiceNo)
    }

    def undoExpanseInvoiceInformation(invoiceNo, amount) {
        new BudgetViewDatabaseService().executeUpdate("UPDATE invoice_expense SET paid_amount=paid_amount-'" + amount + "' WHERE id=" + invoiceNo)

        def selectedReconcilatedStatusSql = "SELECT CASE WHEN b.paid_amount<=0 THEN 0 WHEN b.paid_amount<(b.total_gl_amount+total_vat) THEN 2 ELSE 1 END AS paid_status FROM invoice_expense AS b WHERE b.id='${invoiceNo}' ";
        def selectedReconcilatedStatusArr = new BudgetViewDatabaseService().executeQuery(selectedReconcilatedStatusSql)
        def selectedReconcilatedStatus = selectedReconcilatedStatusArr[0][0]
        new BudgetViewDatabaseService().executeUpdate("UPDATE invoice_expense SET paid_status='" + selectedReconcilatedStatus + "' WHERE id=" + invoiceNo)
    }

    def getCompanyDefaultPaymentTerm() {
        def CompanySetupArr = new BudgetViewDatabaseService().executeQueryAtSingle("SELECT paymentTermId FROM bv.CompanySetup where id=1")

        Integer paymentTerm = 0
        if (CompanySetupArr.size()) {
            paymentTerm = CompanySetupArr[0]
        }
        return paymentTerm
    }

    def getReconciliatedAmount(def glAccount, def accountCode, def invoiceId, int masterType) {
        def sql = "SELECT sum(abs(amount)) FROM trans_master where account_code='" + accountCode + "' ";
        if (masterType == 1) {
            sql = sql + "and recenciliation_code='" + invoiceId + "#1' and account_code ='" + glAccount + "'  group by recenciliation_code";
        } else {
            sql = sql + "and recenciliation_code='" + invoiceId + "#2' and account_code ='" + glAccount + "' group by recenciliation_code";
        }
        def result = new BudgetViewDatabaseService().executeQuery(sql);

        return result.size() > 0 ? new Double(result[0][0]) : new Double(0.00);

    }

    def getBankGlFromAccountNo(def bankAccountNo) {

        ArrayList BankAccArr = new ArrayList()
        BankAccArr = new BudgetViewDatabaseService().executeQuery("SELECT c.gl_chart_code,b.bank_account_code FROM company_bank_accounts AS b  " +
                "LEFT JOIN company_bank_gl_relation AS c ON b.bank_account_code=c.bank_account_code WHERE b.bank_account_no='" + bankAccountNo + "'")
        if (BankAccArr.size()) {
            return BankAccArr[0]
        } else {
            return BankAccArr
        }
    }

    //Budget Item Income Details Search
    def searchTypeDropDown(returnIndex, selectionVal, isNull, contextPath = "") {
        /*
       * varName: Variable Name of the core_param Table Ex: INVOICE_FREQUENCE
       * returnIndex: Return field name/id of the selection drop down Ex:invoice_frequency
       * selectionVal: Selection value Ex: weekly
       * GSP usage: <%= "${new CoreParamsHelper().CoreParamsDropDown('INVOICE_FREQUENCE','invoice_frequency','weekly')}" %>
       * */
        //String DataInstance = CoreParams.findByVarName(varName)

        ArrayList mapStatuseArr = new ArrayList()
        def g = new ValidationTagLib()
        mapStatuseArr = [
                ['ps', g.message(code: 'bv.undoReconciliation.Select.label')],
                ['bn', g.message(code: 'byCustomer&BudgetName.dropdown')],
                ['gl', g.message(code: 'byGlAccount.dropdown')],
                ['bp', g.message(code: 'byBookingPeriod.dropdown')]]

        String dropDown = "<select id=\"searchTypeId\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextPath}/BudgetItemIncomeDetails/selectSearchType',success:function(data,textStatus){jQuery('#ajaxComboUpdateDiv').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"searchTypeId\" style=\"width:253px;\">"

        //   String dropDown = "<select id='" + returnIndex + "'>"

        for (int i = 0; i < mapStatuseArr.size(); i++) {
            if (mapStatuseArr[i][0].toString() == selectionVal) {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "' selected='selected'>" + mapStatuseArr[i][1] + "</option>"
            } else {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "'>" + mapStatuseArr[i][1] + "</option>"
            }
        }

        dropDown += "</select>"
        return dropDown

    }


    def getAbroadDropDown(returnIndex, selectionVal) {
        ArrayList mapStatuseArr = new ArrayList()
        mapStatuseArr = [
                ['1', 'True'],
                ['0', 'False']
        ]
        def dropDown = "<select id='${returnIndex}' name='${returnIndex}'>"
        for (int i = 0; i < mapStatuseArr.size(); i++) {
            if (mapStatuseArr[i][0] == selectionVal.toString()) {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "' selected='selected'>" + mapStatuseArr[i][1] + "</option>"
            } else {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "'>" + mapStatuseArr[i][1] + "</option>"
            }
        }

        dropDown += "</select>"
        return dropDown

    }

    //Budget Item Expense Details Search
    def searchExpenseDropDown(returnIndex, selectionVal, isNull, contextPath = "") {
        /*
       * varName: Variable Name of the core_param Table Ex: INVOICE_FREQUENCE
       * returnIndex: Return field name/id of the selection drop down Ex:invoice_frequency
       * selectionVal: Selection value Ex: weekly
       * GSP usage: <%= "${new CoreParamsHelper().CoreParamsDropDown('INVOICE_FREQUENCE','invoice_frequency','weekly')}" %>
       * */
        //String DataInstance = CoreParams.findByVarName(varName)

        ArrayList mapStatuseArr = new ArrayList()
        def g = new ValidationTagLib()
        mapStatuseArr = [
                ['ps', g.message(code: 'bv.undoReconciliation.Select.label')],
                ['vn', g.message(code: 'byVendor&BudgetItemName.dropdown')],
                ['gl', g.message(code: 'byGlAccount.dropdown')],
                ['bp', g.message(code: 'byBookingPeriod.dropdown')]]

        String dropDown = "<select id=\"searchTypeId\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextPath}/BudgetItemExpenseDetails/selectSearchType',success:function(data,textStatus){jQuery('#ajaxComboUpdateDiv').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"searchTypeId\" style=\"width:253px;\">"
        for (int i = 0; i < mapStatuseArr.size(); i++) {
            if (mapStatuseArr[i][0].toString() == selectionVal) {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "' selected='selected'>" + mapStatuseArr[i][1] + "</option>"
            } else {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "'>" + mapStatuseArr[i][1] + "</option>"
            }
        }

        dropDown += "</select>"
        return dropDown

    }

    //  Manual Entry of Bank Statement Search
    def searchManualDropDown(returnIndex, selectionVal, isNull, contextPath = "") {

        ArrayList mapStatuseArr = new ArrayList()
        def g = new ValidationTagLib()
        mapStatuseArr = [
                ['ps', g.message(code: 'bv.undoReconciliation.Select.label')],
                ['td', g.message(code: 'bv.autoReconciliationOpenInvoicesTransactionDate.label')],
                ['cb', g.message(code: 'bv.manualBankEntryTransBankAccountNo.label')]
        ]

        String dropDown = "<select id=\"searchTypeId\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextPath}/ManualEntryOfBankStatement/selectSearchTypeManual',success:function(data,textStatus){jQuery('#ajaxComboUpdateDiv').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"searchTypeId\" style=\"width:253px;\">"

        for (int i = 0; i < mapStatuseArr.size(); i++) {
            if (mapStatuseArr[i][0].toString() == selectionVal) {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "' selected='selected'>" + mapStatuseArr[i][1] + "</option>"
            } else {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "'>" + mapStatuseArr[i][1] + "</option>"
            }
        }

        dropDown += "</select>"
        return dropDown

    }

    def getDropDownBudgetDetailsType(returnIndex, selectIndex) {

        def g = new ValidationTagLib()
        ArrayList mapTypeArr = new ArrayList()
        mapTypeArr = [
                ['incNexp', g.message(code: 'bv.dashboard.income&Expense.dropdown')],
                ['income', g.message(code: 'bv.dashboard.income.dropdown')],
                ['expense', g.message(code: 'bv.dashboard.expense.dropdown')],
                ['reserve', g.message(code: 'bv.dashboard.reserve.dropdown')],
                /* ['privateSpend',g.message(code: 'bv.dashboard.privateSpend.dropdown')]*/
                ['privateSpend', g.message(code: 'bv.privateBudgetIncomeExpense.dropdown')]

        ]

        String dropDown = "<select class=\"styled sidebr01\" id=\"${returnIndex}\" onchange=\"onChangeBudgetDetails(this.value)\" name=\"${returnIndex}\">"

        if (mapTypeArr.size()) {
            for (int i = 0; i < mapTypeArr.size(); i++) {

                if (selectIndex == mapTypeArr[i][0]) {
                    dropDown += "<option value='" + mapTypeArr[i][0] + "' selected>" + mapTypeArr[i][1] + "</option>"

                } else {
                    dropDown += "<option value='" + mapTypeArr[i][0] + "' >" + mapTypeArr[i][1] + "</option>"
                }
            }

        }

        dropDown += "</select>"
        return dropDown
    }

    def getDropDownBudgetDetailsSortType(returnIndex, selectIndex) {

        def g = new ValidationTagLib()
        ArrayList mapTypeArr = new ArrayList()
        mapTypeArr = [['name_wise', g.message(code: 'bv.dashboard.nameWise.dropdown')],
                      ['acc_head_wise', g.message(code: 'dashboard.accountHeadWise.label')]
        ]

        String dropDown = "<select class=\"styled sidebr01\" id=\"${returnIndex}\" onchange=\"onChangeBudgetDetailsSortType(this.value)\" name=\"${returnIndex}\">"

        if (mapTypeArr.size()) {
            for (int i = 0; i < mapTypeArr.size(); i++) {

                if (selectIndex == mapTypeArr[i][0]) {
                    dropDown += "<option value='" + mapTypeArr[i][0] + "' selected>" + mapTypeArr[i][1] + "</option>"

                } else {
                    dropDown += "<option value='" + mapTypeArr[i][0] + "' >" + mapTypeArr[i][1] + "</option>"
                }
            }

        }

        dropDown += "</select>"
        return dropDown
    }

    def getDropDownTaxReservationType(returnIndex, selectIndex) {

        def g = new ValidationTagLib()
        ArrayList mapTypeArr = new ArrayList()
        //With tax reservation means that part of the profit is reserved for tax so the displayed amount for profit is lower.
        //This is why, tax reservation is reversed
        mapTypeArr = [['taxWithoutReservation', g.message(code: 'bv.dashboardDetails.withTaxReservation.label')],
                      ['taxWithReservation', g.message(code: 'bv.dashboardDetails.withoutTaxReservation.label')],
        ]

        String dropDown = "<select id=\"${returnIndex}\" onchange=\"onChangeTaxReservationType(this.value)\" name=\"${returnIndex}\">"

        if (mapTypeArr.size()) {
            for (int i = 0; i < mapTypeArr.size(); i++) {

                if (selectIndex == mapTypeArr[i][0]) {
                    dropDown += "<option value='" + mapTypeArr[i][0] + "' selected>" + mapTypeArr[i][1] + "</option>"

                } else {
                    dropDown += "<option value='" + mapTypeArr[i][0] + "' >" + mapTypeArr[i][1] + "</option>"
                }
            }

        }

        dropDown += "</select>"
        return dropDown
    }

    def getDropDownForBudget(returnIndex, selectIndex) {

        def g = new ValidationTagLib()
        ArrayList mapTypeArr = new ArrayList()
        mapTypeArr = [['incomeNexpense', g.message(code: 'bv.dashboard.income&Expense.dropdown')],
//                      ['reservationBudget',g.message(code: 'reservationBudgetItemDetails.title')],
['reservationBudget', g.message(code: 'reservationBudgetItemDetails.title')],
['privateBudget', g.message(code: 'privateBudgetMaster.label')],
        ]

//        String dropDown = "<select id=\"${returnIndex}\" name=\"${returnIndex}\">"
        String dropDown = "<select id=\"${returnIndex}\" onchange=\"onChangeBudgetDetails()\" name=\"${returnIndex}\">"

        if (mapTypeArr.size()) {
            for (int i = 0; i < mapTypeArr.size(); i++) {

                if (selectIndex == mapTypeArr[i][0]) {
                    dropDown += "<option value='" + mapTypeArr[i][0] + "' selected>" + mapTypeArr[i][1] + "</option>"

                } else {
                    dropDown += "<option value='" + mapTypeArr[i][0] + "' >" + mapTypeArr[i][1] + "</option>"
                }
            }

        }

        dropDown += "</select>"
        return dropDown
    }

    def getDropDownBudgetSummaryType(returnIndex, selectIndex) {

        def g = new ValidationTagLib()
        ArrayList mapTypeArr = new ArrayList()
        mapTypeArr = [['budNBook', g.message(code: 'bv.dashboardDetails.BudgetsAndBookings.label')],
                      ['budNForcast', g.message(code: 'bv.dashboardDetails.budget&Forcast.label')],
                      ['bookNForecast', g.message(code: 'bv.dashboardDetails.forcast&Booking.label')]
        ]

        String dropDown = "<select id=\"${returnIndex}\" onchange=\"onChangeSummaryDetails(this.value)\" name=\"${returnIndex}\">"

        if (mapTypeArr.size()) {
            for (int i = 0; i < mapTypeArr.size(); i++) {

                if (selectIndex == mapTypeArr[i][0]) {
                    dropDown += "<option value='" + mapTypeArr[i][0] + "' selected>" + mapTypeArr[i][1] + "</option>"

                } else {
                    dropDown += "<option value='" + mapTypeArr[i][0] + "' >" + mapTypeArr[i][1] + "</option>"
                }
            }

        }

        dropDown += "</select>"
        return dropDown
    }

    def searchInvoiceDropDown(returnIndex, selectionVal, isNull, contextPath = "") {
        /*
       * varName: Variable Name of the core_param Table Ex: INVOICE_FREQUENCE
       * returnIndex: Return field name/id of the selection drop down Ex:invoice_frequency
       * selectionVal: Selection value Ex: weekly
       * GSP usage: <%= "${new CoreParamsHelper().CoreParamsDropDown('INVOICE_FREQUENCE','invoice_frequency','weekly')}" %>
       * */
        //String DataInstance = CoreParams.findByVarName(varName)

        ArrayList mapStatuseArr = new ArrayList()
        def g = new ValidationTagLib()
        mapStatuseArr = [
                ['ps', g.message(code: 'bv.undoReconciliation.Select.label')],
                ['cn', g.message(code: 'byCustomerName.dropdown')],
                ['pr', g.message(code: 'byPaymentReference.dropdown')],
                ['bp', g.message(code: 'byBookingPeriod.dropdown')]]

        String dropDown = "<select id=\"searchTypeId\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextPath}/InvoiceIncome/selectSearchType',success:function(data,textStatus){jQuery('#ajaxComboUpdateDiv').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"searchTypeId\" style=\"width:253px;\">"

        //   String dropDown = "<select id='" + returnIndex + "'>"

        for (int i = 0; i < mapStatuseArr.size(); i++) {
            if (mapStatuseArr[i][0].toString() == selectionVal) {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "' selected='selected'>" + mapStatuseArr[i][1] + "</option>"
            } else {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "'>" + mapStatuseArr[i][1] + "</option>"
            }
        }

        dropDown += "</select>"
        return dropDown

    }


    def searchIncomeBookedDropDown(returnIndex, selectionVal, isNull, contextPath = "") {
        /*
       * varName: Variable Name of the core_param Table Ex: INVOICE_FREQUENCE
       * returnIndex: Return field name/id of the selection drop down Ex:invoice_frequency
       * selectionVal: Selection value Ex: weekly
       * GSP usage: <%= "${new CoreParamsHelper().CoreParamsDropDown('INVOICE_FREQUENCE','invoice_frequency','weekly')}" %>
       * */
        //String DataInstance = CoreParams.findByVarName(varName)

        ArrayList mapStatuseArr = new ArrayList()
        def g = new ValidationTagLib()
        mapStatuseArr = [
                ['ps', g.message(code: 'bv.undoReconciliation.Select.label')],
                ['cn', g.message(code: 'byCustomerName.dropdown')],
                ['pr', g.message(code: 'byPaymentReference.dropdown')],
                ['bp', g.message(code: 'byBookingPeriod.dropdown')]]

        String dropDown = "<select id=\"searchTypeId\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextPath}/ReportIncomeBudget/selectSearchType',success:function(data,textStatus){jQuery('#ajaxComboUpdateDiv').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"searchTypeId\" style=\"width:253px;\">"

        //   String dropDown = "<select id='" + returnIndex + "'>"

        for (int i = 0; i < mapStatuseArr.size(); i++) {
            if (mapStatuseArr[i][0].toString() == selectionVal) {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "' selected='selected'>" + mapStatuseArr[i][1] + "</option>"
            } else {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "'>" + mapStatuseArr[i][1] + "</option>"
            }
        }

        dropDown += "</select>"
        return dropDown

    }

    def bookedExpenseDropDown(returnIndex, selectionVal, isNull, contextPath = "") {
        /*
       * varName: Variable Name of the core_param Table Ex: INVOICE_FREQUENCE
       * returnIndex: Return field name/id of the selection drop down Ex:invoice_frequency
       * selectionVal: Selection value Ex: weekly
       * GSP usage: <%= "${new CoreParamsHelper().CoreParamsDropDown('INVOICE_FREQUENCE','invoice_frequency','weekly')}" %>
       * */
        //String DataInstance = CoreParams.findByVarName(varName)

        ArrayList mapStatuseArr = new ArrayList()
        def g = new ValidationTagLib()
        mapStatuseArr = [
                ['ps', g.message(code: 'bv.undoReconciliation.Select.label')],
                ['vn', g.message(code: 'byVendorName.dropdown')],
                ['pr', g.message(code: 'byPaymentReference.dropdown')],
                ['bp', g.message(code: 'byBookingPeriod.dropdown')]]

        String dropDown = "<select id=\"searchTypeId\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextPath}/ReportExpenseBudget/selectSearchType',success:function(data,textStatus){jQuery('#ajaxComboUpdateDiv').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"searchTypeId\" style=\"width:253px;\">"

        //   String dropDown = "<select id='" + returnIndex + "'>"

        for (int i = 0; i < mapStatuseArr.size(); i++) {
            if (mapStatuseArr[i][0].toString() == selectionVal) {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "' selected='selected'>" + mapStatuseArr[i][1] + "</option>"
            } else {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "'>" + mapStatuseArr[i][1] + "</option>"
            }
        }

        dropDown += "</select>"
        return dropDown

    }

    def searchExpenseInvoiceDropDown(returnIndex, selectionVal, isNull, contextPath = "") {
        /*
       * varName: Variable Name of the core_param Table Ex: INVOICE_FREQUENCE
       * returnIndex: Return field name/id of the selection drop down Ex:invoice_frequency
       * selectionVal: Selection value Ex: weekly
       * GSP usage: <%= "${new CoreParamsHelper().CoreParamsDropDown('INVOICE_FREQUENCE','invoice_frequency','weekly')}" %>
       * */
        //String DataInstance = CoreParams.findByVarName(varName)

        ArrayList mapStatuseArr = new ArrayList()
        def g = new ValidationTagLib()
        mapStatuseArr = [
                ['ps', g.message(code: 'bv.undoReconciliation.Select.label')],
                ['vn', g.message(code: 'byVendorName.dropdown')],
                ['pr', g.message(code: 'byPaymentReference.dropdown')],
                ['bp', g.message(code: 'byBookingPeriod.dropdown')]]

        String dropDown = "<select id=\"searchTypeId\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextPath}/InvoiceExpense/selectSearchType',success:function(data,textStatus){jQuery('#ajaxComboUpdateDiv').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"searchTypeId\" style=\"width:253px;\">"

        //   String dropDown = "<select id='" + returnIndex + "'>"

        for (int i = 0; i < mapStatuseArr.size(); i++) {
            if (mapStatuseArr[i][0].toString() == selectionVal) {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "' selected='selected'>" + mapStatuseArr[i][1] + "</option>"
            } else {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "'>" + mapStatuseArr[i][1] + "</option>"
            }
        }

        dropDown += "</select>"
        return dropDown

    }

    def searchReceiptDropDown(returnIndex, selectionVal, isNull, contextPath = "") {
        /*
       * varName: Variable Name of the core_param Table Ex: INVOICE_FREQUENCE
       * returnIndex: Return field name/id of the selection drop down Ex:invoice_frequency
       * selectionVal: Selection value Ex: weekly
       * GSP usage: <%= "${new CoreParamsHelper().CoreParamsDropDown('INVOICE_FREQUENCE','invoice_frequency','weekly')}" %>
       * */
        //String DataInstance = CoreParams.findByVarName(varName)

        ArrayList mapStatuseArr = new ArrayList()
        def g = new ValidationTagLib()
        mapStatuseArr = [
                ['ps', g.message(code: 'bv.undoReconciliation.Select.label')],
                ['vn', g.message(code: 'byVendorName.dropdown')],
                ['pr', g.message(code: 'byPaymentReference.dropdown')],
                ['bp', g.message(code: 'byBookingPeriod.dropdown')]]

        String dropDown = "<select id=\"searchTypeId\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextPath}/InvoiceExpense/selectSearchTypeReceipt',success:function(data,textStatus){jQuery('#ajaxComboUpdateDiv').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"searchTypeId\" style=\"width:253px;\">"

        //   String dropDown = "<select id='" + returnIndex + "'>"

        for (int i = 0; i < mapStatuseArr.size(); i++) {
            if (mapStatuseArr[i][0].toString() == selectionVal) {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "' selected='selected'>" + mapStatuseArr[i][1] + "</option>"
            } else {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "'>" + mapStatuseArr[i][1] + "</option>"
            }
        }

        dropDown += "</select>"
        return dropDown

    }

    def searchCustomer(returnIndex, selectionVal, isNull, contextPath = "") {
        /*
       * varName: Variable Name of the core_param Table Ex: INVOICE_FREQUENCE
       * returnIndex: Return field name/id of the selection drop down Ex:invoice_frequency
       * selectionVal: Selection value Ex: weekly
       * GSP usage: <%= "${new CoreParamsHelper().CoreParamsDropDown('INVOICE_FREQUENCE','invoice_frequency','weekly')}" %>
       * */
        //String DataInstance = CoreParams.findByVarName(varName)

        ArrayList mapStatuseArr = new ArrayList()
        def g = new ValidationTagLib()
        mapStatuseArr = [
                ['ps', g.message(code: 'bv.undoReconciliation.Select.label')],
                ['cn', g.message(code: 'byCustomerName.dropdown')],
                ['gl', g.message(code: 'byDeafaultGlAccount.dropdown')]]


        String dropDown = "<select id=\"searchTypeId\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextPath}/CustomerMaster/selectSearchType',success:function(data,textStatus){jQuery('#ajaxComboUpdateDiv').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"searchTypeId\" style=\"width:253px;\">"

        //   String dropDown = "<select id='" + returnIndex + "'>"

        for (int i = 0; i < mapStatuseArr.size(); i++) {
            if (mapStatuseArr[i][0].toString() == selectionVal) {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "' selected='selected'>" + mapStatuseArr[i][1] + "</option>"
            } else {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "'>" + mapStatuseArr[i][1] + "</option>"
            }
        }

        dropDown += "</select>"
        return dropDown

    }

    def searchVendor(returnIndex, selectionVal, isNull, contextPath = "") {
        /*
       * varName: Variable Name of the core_param Table Ex: INVOICE_FREQUENCE
       * returnIndex: Return field name/id of the selection drop down Ex:invoice_frequency
       * selectionVal: Selection value Ex: weekly
       * GSP usage: <%= "${new CoreParamsHelper().CoreParamsDropDown('INVOICE_FREQUENCE','invoice_frequency','weekly')}" %>
       * */
        //String DataInstance = CoreParams.findByVarName(varName)

        ArrayList mapStatuseArr = new ArrayList()
        def g = new ValidationTagLib()
        mapStatuseArr = [
                ['ps', g.message(code: 'bv.undoReconciliation.Select.label')],
                ['vn', g.message(code: 'byVendorName.dropdown')],
                ['gl', g.message(code: 'byDeafaultGlAccount.dropdown')]]


        String dropDown = "<select id=\"searchTypeId\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextPath}/VendorMaster/selectSearchType',success:function(data,textStatus){jQuery('#ajaxComboUpdateDiv').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"searchTypeId\" style=\"width:253px;\">"

        //   String dropDown = "<select id='" + returnIndex + "'>"

        for (int i = 0; i < mapStatuseArr.size(); i++) {
            if (mapStatuseArr[i][0].toString() == selectionVal) {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "' selected='selected'>" + mapStatuseArr[i][1] + "</option>"
            } else {
                dropDown += "<option value='" + mapStatuseArr[i][0] + "'>" + mapStatuseArr[i][1] + "</option>"
            }
        }

        dropDown += "</select>"
        return dropDown
    }

    def getVatCategoryIdFromRate(vatRate) {
        def listArr = new BudgetViewDatabaseService().executeQuery("SELECT rate,id FROM VatCategory Where rate=" + vatRate)
        return listArr[0][1]
    }

    def getVatCategoryIdFromCategoryName(CategoryName) {
        def listArr = new BudgetViewDatabaseService().executeQuery("SELECT rate,id FROM VatCategory Where category_name='${CategoryName}'")
        return listArr[0][1]
    }

    def getStatus(def fName) {

        String dropDown = "<select id='status' name='status'>"
        dropDown += "<option value='1'> Active</option>"
        dropDown += "<option value='0'> Inactive</option>"
        dropDown += "</select>"
        return dropDown
    }

    def getAjaxGLAccountReservationBudgetForReconcilationDropDown(returnIndex, selectValName, contextCustomer = "", budgetId, selectedByBankAccountNo = "") {

        def AllBudgetAccountArr = new BudgetViewDatabaseService().executeQueryAtSingle("SELECT b.gl_account AS budgetAccount FROM reservation_budget_item AS a LEFT JOIN reservation_budget_item_details AS b ON a.id=b.reserv_budget_item_id WHERE a.budget_name_id='$budgetId' GROUP BY b.gl_account ORDER BY b.gl_account ")
//        def AllBudgetAccountArr = new BudgetViewDatabaseService().executeQueryAtSingle("SELECT b.gl_account AS budgetAccount FROM budget_item_expense AS a LEFT JOIN budget_item_expense_details AS b ON a.id=b.budget_item_expense_id WHERE a.vendor_id='$budgetId' GROUP BY b.gl_account ORDER BY b.gl_account ")

        // String dropDown = "<select name='" + returnIndex + "'>"
        String dropDown = "<select id=\"${returnIndex}\" onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value, url:'${contextCustomer}/BankReconciliation/budgetCodeWiseReservationGLCode',success:function(data,textStatus){jQuery('#ajaxbudgetCodeWiseExpanseGLCodeDiv').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"${returnIndex}\" style=\"\">"

        if (AllBudgetAccountArr.size()) {
            def tempAccount = AllBudgetAccountArr.join(",")
            def CatArr = new BudgetViewDatabaseService().executeQuery("SELECT a.id,a.name AS categoryName FROM chart_group AS a LEFT JOIN chart_master AS b ON a.id=b.chart_group_id WHERE a.status=1 AND b.account_code IN($tempAccount) GROUP BY a.id")


            if (CatArr.size()) {
                for (int i = 0; i < CatArr.size(); i++) {
                    def catId = CatArr[i][0]
                    def ProductArr
                    ProductArr = new BudgetViewDatabaseService().executeQuery("SELECT id,accountCode,accountName FROM bv.ChartMaster where status='1' AND chart_group_id='" + catId + "' AND account_code IN($tempAccount)")
                    if (ProductArr.size()) {
                        dropDown += "<optgroup label='" + CatArr[i][1] + "'>"
                        for (int j = 0; j < ProductArr.size(); j++) {

                            if (selectValName == ProductArr[j][1]) {

                                if (selectedByBankAccountNo.trim()) {
                                    dropDown += "<option value='" + ProductArr[j][1] + "::" + selectedByBankAccountNo + "::" + budgetId + "' selected>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                                } else {
                                    dropDown += "<option value='" + ProductArr[j][1] + "' selected>" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                                }

                            } else {
                                if (selectedByBankAccountNo.trim()) {
                                    dropDown += "<option value='" + ProductArr[j][1] + "::" + selectedByBankAccountNo + "::" + budgetId + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                                } else {
                                    dropDown += "<option value='" + ProductArr[j][1] + "' >" + ProductArr[j][1] + "  " + ProductArr[j][2] + "</option>"
                                }

                            }
                        }
                        dropDown += "</optgroup>"
                    }
                }
            }

        }
        dropDown += "</select>"
        return dropDown
    }


    def sortingBankStatementFileDropDown(returnIndex, selectIndex, contextPath) {

        ArrayList categoryArr = new ArrayList()
        def g = new ValidationTagLib()
        categoryArr = [
                ['bbs', g.message(code: 'listOfBankstatementFile.byBankStatementFile.dropdown')],
                ['bba', g.message(code: 'listOfBankstatementFile.byBankAccountNo.dropdown')]
        ]


        String dropDown = "<select id=\"${returnIndex}\"  onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value,  url:'${contextPath}/reportReconciliation/sortBankStatementList',success:function(data,textStatus){jQuery('#sortingDiv').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"${returnIndex}\" required>"

        for (int i = 0; i < categoryArr.size(); i++) {
            if (categoryArr[i][0].toString() == selectIndex) {
                dropDown += "<option value='" + categoryArr[i][0] + "' selected='selected'>" + categoryArr[i][1] + "</option>"
            } else {
                dropDown += "<option value='" + categoryArr[i][0] + "'>" + categoryArr[i][1] + "</option>"
            }
        }

        dropDown += "</select>"
        return dropDown

    }


    def filterInvoicesDropDown(returnIndex, selectIndex, contextPath) {

        ArrayList categoryArr = new ArrayList()
        def g = new ValidationTagLib()
        categoryArr = [
                ['expRec', g.message(code: 'listOfExpenseInvoices.expense&Receipt.dropdown')],
                ['expense', g.message(code: 'listOfExpenseInvoices.expenseInvoices.dropdown')],
                ['receipt', g.message(code: 'listOfExpenseInvoices.receipt.dropdown')]

        ]

        String dropDown = "<select id=\"${returnIndex}\"  onchange=\"jQuery.ajax({type:'POST',data:'id=' + this.value,  url:'${contextPath}/reportExpenseBudget/filterInvoicesByDropDown',success:function(data,textStatus){jQuery('#filterDiv').html(data);},error:function(XMLHttpRequest,textStatus,errorThrown){}});\" name=\"${returnIndex}\" required>"

        for (int i = 0; i < categoryArr.size(); i++) {
            if (categoryArr[i][0].toString() == selectIndex) {
                dropDown += "<option value='" + categoryArr[i][0] + "' selected='selected'>" + categoryArr[i][1] + "</option>"
            } else {
                dropDown += "<option value='" + categoryArr[i][0] + "'>" + categoryArr[i][1] + "</option>"
            }
        }

        dropDown += "</select>"
        return dropDown

    }
}
