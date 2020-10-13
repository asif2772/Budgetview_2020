package bv

class BudgetDetailsTagLib {
    static defaultEncodeAs = 'html'
    //static encodeAsForTags = [tagName: 'raw']
    /**
     * Reservation Area
     * */

    def getResevationType(returnIndex, selectIndex) {

        def mapReservationTypeArr = getReservationTypeArr()

        String dropDown = "<select required class='styled sidebr01' name='" + returnIndex + "' id='" + returnIndex + "'tabindex=\"8\" >"
        dropDown += "<option value=''>" + g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"
        if (mapReservationTypeArr.size()) {
            for (int i = 0; i < mapReservationTypeArr.size(); i++) {
                if (mapReservationTypeArr[i][0] == selectIndex) {
                    dropDown += "<option value='" + mapReservationTypeArr[i][0] + "' selected='selected'>" + mapReservationTypeArr[i][1] + "</option>"
                } else {
                    dropDown += "<option value='" + mapReservationTypeArr[i][0] + "'>" + mapReservationTypeArr[i][1] + "</option>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }

    def getResBankInfoStatus(returnIndex, selectIndex) {

//        def mapReservationTypeArr = getReservationTypeArr()

        String dropDown = "<select class='styled sidebr01' name='" + returnIndex + "' id='" + returnIndex + "'tabindex=\"8\" >"

        if (selectIndex == '0') {
            dropDown += "<option value='0' selected='selected'>Inactive</option>"
            dropDown += "<option value='1' >Active</option>"

        }
        else{
            dropDown += "<option value='0' >Inactive</option>"
            dropDown += "<option value='1' selected='selected'>Active</option>"
        }
        dropDown += "</select>"
        return dropDown
    }


    def getPrivateType(returnIndex, selectIndex) {

        ArrayList mapPrivateTypeArr = new ArrayList()
        mapPrivateTypeArr = [
               /* ['0', g.message(code: 'reservationMaster.typeOfReservation0.dropDown')],*/
                ['1', g.message(code: 'privateMaster.typeOfprivate1.dropDown')],
                ['2', g.message(code: 'privateMaster.typeOfprivate2.dropDown')],
                ['3', g.message(code: 'privateMaster.typeOfprivate3.dropDown')]
        ]

        String dropDown = "<select class='styled sidebr01' name='" + returnIndex + "' id='" + returnIndex + "'tabindex=\"8\" required >"
        dropDown += "<option value=\"\" selected>" +g.message(code: 'bv.undoReconciliation.Select.label') + "</option>"

        if (mapPrivateTypeArr.size()) {
            for (int i = 0; i < mapPrivateTypeArr.size(); i++) {
                if (mapPrivateTypeArr[i][0] == selectIndex.toString()) {
                    dropDown += "<option value='" + mapPrivateTypeArr[i][0] + "' selected='selected'>" + mapPrivateTypeArr[i][1] + "</option>"
                } else {
                    dropDown += "<option value='" + mapPrivateTypeArr[i][0] + "'>" + mapPrivateTypeArr[i][1] + "</option>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }



    def getGeneratedReservationCode() {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=14")
        def Prefix = PrefixDataArr[0][0]
        def PrefixLength = PrefixDataArr[0][1]
        def reservationCode = new CoreParamsHelper().getCodeSequence(Prefix,PrefixLength)

        return reservationCode
    }


/**
 * Prvate Budget Area
 * */

    def getGeneratedPrivateCode() {
        def PrefixDataArr = new BudgetViewDatabaseService().executeQuery("SELECT prefix,prefixLen FROM bv.SystemPrefix where id=13")
        def Prefix = PrefixDataArr[0][0]
        def PrefixLength = PrefixDataArr[0][1]
        def reservationCode = new CoreParamsHelper().getCodeSequence(Prefix,PrefixLength)

        return reservationCode
    }

    /**
     * common Area
     * */

    def getVatCategory(returnIndex, selectIndex) {

        def VatCatArr = new BudgetViewDatabaseService().executeQuery("SELECT id,categoryName,rate FROM VatCategory WHERE status=1")
        String dropDown = "<select class='styled sidebr01' name='" + returnIndex + "' id='" + returnIndex + "'tabindex=\"8\" >"
        if (VatCatArr.size()) {
            for (int i = 0; i < VatCatArr.size(); i++) {
                if (VatCatArr[i][0] ==  selectIndex) {
                    dropDown += "<option value='" + VatCatArr[i][0] + "' selected='selected'>" + VatCatArr[i][1] + " (" + VatCatArr[i][2] + "%)" + "</option>"
                } else {
                    dropDown += "<option value='" + VatCatArr[i][0] + "'>" + VatCatArr[i][1] + " (" + VatCatArr[i][2] + "%)" + "</option>"
                }
            }
        } else {
            dropDown += "<option>"+ g.message(code: 'coreParamsHelper.noVatCategorySetup.label') +"</option>"
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

    def getReservationTypeArr(){
        ArrayList mapReservationTypeArr = new ArrayList()
        mapReservationTypeArr = [
               /* ['0', g.message(code: 'reservationMaster.typeOfReservation0.dropDown')],*/
                ['1', g.message(code: 'reservationMaster.typeOfReservation1.dropDown')],
                ['2', g.message(code: 'reservationMaster.typeOfReservation2.dropDown')],
                ['3', g.message(code: 'reservationMaster.typeOfReservation3.dropDown')],
                ['4', g.message(code: 'reservationMaster.typeOfReservation4.dropDown')],
                ['5', g.message(code: 'reservationMaster.typeOfReservation5.dropDown')],
                ['6', g.message(code: 'reservationMaster.typeOfReservation6.dropDown')],
        ]
        return mapReservationTypeArr;
    }


    def getBudgetDuration(returnIndex, selectIndex) {

        ArrayList mapBudgetDurationArr = new ArrayList()
        mapBudgetDurationArr = [
//                ['0', g.message(code: 'reservationMaster.typeOfReservation0.dropDown')],
                ['2', g.message(code: '2')],
                ['3', g.message(code: '3')],
                ['4', g.message(code: '4')],
                ['5', g.message(code: '5')],
        ]

        String dropDown = "<select style=\"width: 68px; height: 30.5px;\" name='" + returnIndex + "' id='" +
                returnIndex + "'tabindex=\"8\" >"
        if (mapBudgetDurationArr.size()) {
            for (int i = 0; i < mapBudgetDurationArr.size(); i++) {
                if (mapBudgetDurationArr[i][0] == selectIndex) {
                    dropDown += "<option value='" + mapBudgetDurationArr[i][0] + "' selected='selected'>" + mapBudgetDurationArr[i][1] + "</option>"
                } else {
                    dropDown += "<option value='" + mapBudgetDurationArr[i][0] + "'>" + mapBudgetDurationArr[i][1] + "</option>"
                }
            }
        }
        dropDown += "</select>"
        return dropDown
    }



}
