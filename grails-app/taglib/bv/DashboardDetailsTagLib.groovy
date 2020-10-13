package bv

class DashboardDetailsTagLib {
    static defaultEncodeAs = [taglib:'html']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    def Double getRoundedValue(Double dValue) {

        Double dRounded = 0.0;
        if (dValue < 0) {
            dRounded = dValue - 0.5;
            dRounded = Math.ceil(dRounded);
        }else {
            dRounded = dValue + 0.5;
            dRounded = Math.floor(dRounded);
        }

        return dRounded;
    }

    def getInvoiceExpenseBarColor(invoiceAmount, budgetAmount) {

        float percentageValue = 0;
        if (invoiceAmount > 0 && budgetAmount > 0) {
            percentageValue = (invoiceAmount * 100.0) / budgetAmount;
        }
        def barColor = 'rgba(18,243,14,.9)'//Green

        //println("percentageValue "+percentageValue);

        if (percentageValue > 120.0) {
            barColor = 'rgba(255,0,0,.9)'//Red color
        } else if (percentageValue > 100.0 && percentageValue <= 120.0) {
            barColor = 'rgba(255,102,0,.9)'//Orange Color
        } else if (percentageValue >= 0.0 && percentageValue < 80.0) {
            barColor = 'rgba(255,102,0,.9)'//Orange Color
        } else if (percentageValue >= 80.0 && percentageValue <= 100.0) {
            barColor = 'rgba(18,243,14,.9)'//Green
        }

        return barColor
    }

    def getInvoiceIncomeBarColor(invoiceAmount, budgetAmount) {

        float percentageValue = 0.0;
        if (invoiceAmount > 0 && budgetAmount > 0) {
            percentageValue = (invoiceAmount * 100.0) / budgetAmount;
        }
        def barColor = 'rgba(255,102,0,.9)'//Green
        //println("percentageValue "+percentageValue);

        if (percentageValue > 120.0) {
            barColor = 'rgba(18,243,14,.9)'//Green color
        } else if (percentageValue > 100.0 && percentageValue <= 120.0) {
            barColor = 'rgba(18,243,14,.9)'//Green Color
        } else if (percentageValue >= 0.0 && percentageValue < 80.0) {
            barColor = 'rgba(255,0,0,.9)'//Red Color
        } else if (percentageValue >= 80.0 && percentageValue <= 100.0) {
            barColor = 'rgba(255,102,0,.9)'//Orange
        }

        return barColor
    }

    def getPrivateInvoiceBarColor(invoiceAmount, budgetAmount) {

        boolean bExpenseInv = false;
        float percentageValue = 0.0;
        if (invoiceAmount > 0 && budgetAmount > 0) {
            percentageValue = (invoiceAmount * 100.0) / budgetAmount;
        }
        else if (invoiceAmount < 0 && budgetAmount < 0) {
            bExpenseInv = true;
            percentageValue = (invoiceAmount * 100.0) / budgetAmount;
        }
        def barColor = 'rgba(255,102,0,.9)'//Green
        //println("percentageValue "+percentageValue);
        if(bExpenseInv){
            if (percentageValue > 120.0) {
                barColor = 'rgba(255,0,0,.9)'//Red Color
            } else if (percentageValue > 100.0 && percentageValue <= 120.0) {
                barColor = 'rgba(255,0,0,.9)'//Red Color
            } else if (percentageValue >= 0.0 && percentageValue < 80.0) {
                barColor = 'rgba(18,243,14,.9)'//Green color
            } else if (percentageValue >= 80.0 && percentageValue <= 100.0) {
                barColor = 'rgba(255,102,0,.9)'//Orange
            }
        }else{
            if (percentageValue > 120.0) {
                barColor = 'rgba(18,243,14,.9)'//Green color
            } else if (percentageValue > 100.0 && percentageValue <= 120.0) {
                barColor = 'rgba(18,243,14,.9)'//Green Color
            } else if (percentageValue >= 0.0 && percentageValue < 80.0) {
                barColor = 'rgba(255,0,0,.9)'//Red Color
            } else if (percentageValue >= 80.0 && percentageValue <= 100.0) {
                barColor = 'rgba(255,102,0,.9)'//Orange
            }
        }


        return barColor
    }

    def getSummaryBarColorWithoutCond(forecastAmount, budgetAmount, summaryType = 0) {

        def barColor = 'rgba(255,102,0,.9)'//Orange Color
        if (summaryType == 0) {
            barColor = 'rgba(255,102,0,.9)'//Orange Color
        } else if (summaryType == 1) {
            barColor = getInvoiceIncomeBarColor(forecastAmount, budgetAmount);
        } else if (summaryType == 2) {
            barColor = getInvoiceExpenseBarColor(forecastAmount, budgetAmount);
        }

        return barColor
    }

    def getSummaryViewBarColor(forecastAmount, budgetAmount, summaryType) {

        float percentageValue = 0.0;
        if (forecastAmount > 0 && budgetAmount > 0) {
            percentageValue = (forecastAmount * 100.0) / budgetAmount;
        }
        else if (forecastAmount < 0 && budgetAmount < 0) {
            percentageValue = (forecastAmount * 100.0) / budgetAmount;
            println("forecastAmount: "+forecastAmount + " budgetAmount: "+budgetAmount);
            println("percentageValue "+percentageValue);
        }

        def barColor = 'rgba(18,243,14,.9)'//Green color
        switch (summaryType) {
            case 0://Main

                if (forecastAmount < 0 && budgetAmount < 0) {
                    if (percentageValue > 100.0) {
                        barColor = 'rgba(255,0,0,.9)'//Red color
                    } else if (percentageValue >= 0.0 && percentageValue < 80.0) {
                        barColor = 'rgba(18,243,14,.9)'//Green
                    } else if (percentageValue >= 80.0 && percentageValue <= 100.0) {
                        barColor = 'rgba(255,102,0,.9)'//Orange Color
                    }
                }else{
                    if (percentageValue > 100.0) {
                        barColor = 'rgba(18,243,14,.9)'//Green
                    } else if (percentageValue > 0.0 && percentageValue < 80.0) {
                        barColor = 'rgba(255,0,0,.9)'//Red color
                    } else if (percentageValue >= 80.0 && percentageValue <= 100.0) {
                        barColor = 'rgba(255,102,0,.9)'//Orange Color
                    }
                }
                break;
            case 1://Income
                barColor = getInvoiceIncomeBarColor(forecastAmount, budgetAmount);
                break;
            case 2://Expense
                barColor = getInvoiceExpenseBarColor(forecastAmount, budgetAmount);
                break;
        }

        //Some exceptional cases.
        if (percentageValue == 0.0) {
            if (forecastAmount > 0 && budgetAmount < 0) {
                barColor = 'rgba(18,243,14,.9)'//Green color
            }

            if (forecastAmount < 0 && budgetAmount > 0) {
                barColor = 'rgba(255,0,0,.9)'//Red color
            }
        }

        if (forecastAmount < 0 && budgetAmount < 0) {
            println("barColor "+barColor);
        }

        return barColor
    }

    def getInvoiceLabelColor(invoiceAmount, budgetAmount) {

        float percentageValue = 0.0;
        if (invoiceAmount > 0 && budgetAmount > 0) {
            percentageValue = (invoiceAmount * 100) / budgetAmount;
        }
        def labelColor = '#12f30e'//Green

        if (percentageValue > 120.0) {
            labelColor = '#ff0000'//Red color
        } else if (percentageValue > 80.0 && percentageValue <= 120.0) {
            labelColor = '#FF6600'//Orange Color
        } else if (percentageValue >= 0.0 && percentageValue <= 80.0) {
            labelColor = '#FF6600'//Orange Color
        } else if (percentageValue > 80.0 && percentageValue <= 100.0) {
            labelColor = '#12f30e'//Green
        }

        return labelColor
    }

    def List<Map> getMonthlyBudgetAmount(def glAccountArr, def budgetCustomerIdArr, def budgetAmountArr) {

        List monthlyBudgetArr = new ArrayList();
        Map monthlyAmount = ["janAmount": 0, "febAmount": 0, "marAmount": 0, "aprAmount": 0, "mayAmount": 0, "junAmount": 0,
                             "julAmount": 0, "augAmount": 0, "sepAmount": 0, "octAmount": 0, "novAmount": 0, "decAmount": 0];

        Map monthlyBudgetCount = ["janItemCount": 0, "febItemCount": 0, "marItemCount": 0, "aprItemCount": 0, "mayItemCount": 0, "junItemCount": 0,
                                  "julItemCount": 0, "augItemCount": 0, "sepItemCount": 0, "octItemCount": 0, "novItemCount": 0, "decItemCount": 0];


        Map monthlyGLCode = ["janGLCode": '', "febGLCode": '', "marGLCode": '', "aprGLCode": '', "mayGLCode": '', "junGLCode": '',
                             "julGLCode": '', "augGLCode": '', "sepGLCode": '', "octGLCode": '', "novGLCode": '', "decGLCode": ''];

        Map monthlyBudgetId = ["janBudId": 0, "febBudId": 0, "marBudId": 0, "aprBudId": 0, "mayBudId": 0, "junBudId": 0,
                               "julBudId": 0, "augBudId": 0, "sepBudId": 0, "octBudId": 0, "novBudId": 0, "decBudId": 0];

        Map monthlyBudgetDetailsId = ["janBudDetId": 0, "febBudDetId": 0, "marBudDetId": 0, "aprBudDetId": 0, "mayBudDetId": 0, "junBudDetId": 0,
                                      "julBudDetId": 0, "augBudDetId": 0, "sepBudDetId": 0, "octBudDetId": 0, "novBudDetId": 0, "decBudDetId": 0];

        def budgetIdWiseBudgetCnt = [:]
        for (int u = 0; u < glAccountArr.size(); u++) {
            double summarytotalTempAmount = 0.0

            def glAccountCode = glAccountArr[u];
            def customerId = budgetCustomerIdArr[u];

            for (int x = 0; x < budgetAmountArr.size(); x++) {
                for (int z = 0; z < budgetAmountArr[x].size(); z++) {
                    def tmpCustomerId = budgetAmountArr[x][z][0];

                    if (tmpCustomerId == customerId) {

                        if (glAccountCode == budgetAmountArr[x][z][1]) {

                            def budgetId = budgetAmountArr[x][z][6]
                            //Checking Booking Period
                            if (budgetAmountArr[x][z][3] == 1) {
                                monthlyAmount.janAmount = monthlyAmount.janAmount + budgetAmountArr[x][z][4]

                                def incrementedVal = budgetIdWiseBudgetCnt[budgetId]
                                if (incrementedVal != 1) {
                                    monthlyBudgetCount.janItemCount = monthlyBudgetCount.janItemCount + 1
                                }
                                budgetIdWiseBudgetCnt[budgetId] = 1

                                monthlyBudgetId.janBudId = budgetId
                                monthlyBudgetDetailsId.janBudDetId = budgetAmountArr[x][z][7]

                                if (monthlyGLCode.janGLCode.isEmpty()) {
                                    monthlyGLCode.janGLCode = glAccountCode;
                                } else {
                                    monthlyGLCode.janGLCode = monthlyGLCode.janGLCode + "," + glAccountCode;
                                }

                            } else if (budgetAmountArr[x][z][3] == 2) {
                                monthlyAmount.febAmount = monthlyAmount.febAmount + budgetAmountArr[x][z][4]


                                def incrementedVal = budgetIdWiseBudgetCnt[budgetId]
                                if (incrementedVal != 1) {
                                    monthlyBudgetCount.febItemCount = monthlyBudgetCount.febItemCount + 1
                                }
                                budgetIdWiseBudgetCnt[budgetId] = 1

                                monthlyBudgetId.febBudId = budgetId
                                monthlyBudgetDetailsId.febBudDetId = budgetAmountArr[x][z][7]

                                if (monthlyGLCode.febGLCode.isEmpty()) {
                                    monthlyGLCode.febGLCode = glAccountCode;
                                } else {
                                    monthlyGLCode.febGLCode = monthlyGLCode.febGLCode + "," + glAccountCode;
                                }
                            } else if (budgetAmountArr[x][z][3] == 3) {
                                monthlyAmount.marAmount = monthlyAmount.marAmount + budgetAmountArr[x][z][4]

                                def incrementedVal = budgetIdWiseBudgetCnt[budgetId]
                                if (incrementedVal != 1) {
                                    monthlyBudgetCount.marItemCount = monthlyBudgetCount.marItemCount + 1
                                }
                                budgetIdWiseBudgetCnt[budgetId] = 1

                                monthlyBudgetId.marBudId = budgetId
                                monthlyBudgetDetailsId.marBudDetId = budgetAmountArr[x][z][7]

                                if (monthlyGLCode.marGLCode.isEmpty()) {
                                    monthlyGLCode.marGLCode = glAccountCode;
                                } else {
                                    monthlyGLCode.marGLCode = monthlyGLCode.marGLCode + "," + glAccountCode;
                                }
                            } else if (budgetAmountArr[x][z][3] == 4) {
                                monthlyAmount.aprAmount = monthlyAmount.aprAmount + budgetAmountArr[x][z][4]
                                def incrementedVal = budgetIdWiseBudgetCnt[budgetId]
                                if (incrementedVal != 1) {
                                    monthlyBudgetCount.aprItemCount = monthlyBudgetCount.aprItemCount + 1
                                }
                                budgetIdWiseBudgetCnt[budgetId] = 1

                                monthlyBudgetId.aprBudId = budgetId
                                monthlyBudgetDetailsId.aprBudDetId = budgetAmountArr[x][z][7]

                                if (monthlyGLCode.aprGLCode.isEmpty()) {
                                    monthlyGLCode.aprGLCode = glAccountCode;
                                } else {
                                    monthlyGLCode.aprGLCode = monthlyGLCode.aprGLCode + "," + glAccountCode;
                                }

                            } else if (budgetAmountArr[x][z][3] == 5) {
                                monthlyAmount.mayAmount = monthlyAmount.mayAmount + budgetAmountArr[x][z][4]

                                def incrementedVal = budgetIdWiseBudgetCnt[budgetId]
                                if (incrementedVal != 1) {
                                    monthlyBudgetCount.mayItemCount = monthlyBudgetCount.mayItemCount + 1
                                }
                                budgetIdWiseBudgetCnt[budgetId] = 1

                                monthlyBudgetId.mayBudId = budgetId
                                monthlyBudgetDetailsId.mayBudDetId = budgetAmountArr[x][z][7]

                                if (monthlyGLCode.mayGLCode.isEmpty()) {
                                    monthlyGLCode.mayGLCode = glAccountCode;
                                } else {
                                    monthlyGLCode.mayGLCode = monthlyGLCode.mayGLCode + "," + glAccountCode;
                                }

                            } else if (budgetAmountArr[x][z][3] == 6) {
                                monthlyAmount.junAmount = monthlyAmount.junAmount + budgetAmountArr[x][z][4]

                                def incrementedVal = budgetIdWiseBudgetCnt[budgetId]
                                if (incrementedVal != 1) {
                                    monthlyBudgetCount.junItemCount = monthlyBudgetCount.junItemCount + 1
                                }
                                budgetIdWiseBudgetCnt[budgetId] = 1

                                monthlyBudgetId.junBudId = budgetId
                                monthlyBudgetDetailsId.junBudDetId = budgetAmountArr[x][z][7]

                                if (monthlyGLCode.junGLCode.isEmpty()) {
                                    monthlyGLCode.junGLCode = glAccountCode;
                                } else {
                                    monthlyGLCode.junGLCode = monthlyGLCode.junGLCode + "," + glAccountCode;
                                }

                            } else if (budgetAmountArr[x][z][3] == 7) {
                                monthlyAmount.julAmount = monthlyAmount.julAmount + budgetAmountArr[x][z][4]
                                def incrementedVal = budgetIdWiseBudgetCnt[budgetId]
                                if (incrementedVal != 1) {
                                    monthlyBudgetCount.julItemCount = monthlyBudgetCount.julItemCount + 1
                                }
                                budgetIdWiseBudgetCnt[budgetId] = 1

                                monthlyBudgetId.julBudId = budgetId
                                monthlyBudgetDetailsId.julBudDetId = budgetAmountArr[x][z][6]

                                if (monthlyGLCode.julGLCode.isEmpty()) {
                                    monthlyGLCode.julGLCode = glAccountCode;
                                } else {
                                    monthlyGLCode.julGLCode = monthlyGLCode.julGLCode + "," + glAccountCode;
                                }
                            } else if (budgetAmountArr[x][z][3] == 8) {
                                monthlyAmount.augAmount = monthlyAmount.augAmount + budgetAmountArr[x][z][4]
                                def incrementedVal = budgetIdWiseBudgetCnt[budgetId]
                                if (incrementedVal != 1) {
                                    monthlyBudgetCount.augItemCount = monthlyBudgetCount.augItemCount + 1
                                }
                                budgetIdWiseBudgetCnt[budgetId] = 1

                                monthlyBudgetId.augBudId = budgetId
                                monthlyBudgetDetailsId.augBudDetId = budgetAmountArr[x][z][7]

                                if (monthlyGLCode.augGLCode.isEmpty()) {
                                    monthlyGLCode.augGLCode = glAccountCode;
                                } else {
                                    monthlyGLCode.augGLCode = monthlyGLCode.augGLCode + "," + glAccountCode;
                                }

                            } else if (budgetAmountArr[x][z][3] == 9) {
                                monthlyAmount.sepAmount = monthlyAmount.sepAmount + budgetAmountArr[x][z][4]
                                def incrementedVal = budgetIdWiseBudgetCnt[budgetId]
                                if (incrementedVal != 1) {
                                    monthlyBudgetCount.sepItemCount = monthlyBudgetCount.sepItemCount + 1
                                }
                                budgetIdWiseBudgetCnt[budgetId] = 1

                                monthlyBudgetId.sepBudId = budgetId
                                monthlyBudgetDetailsId.sepBudDetId = budgetAmountArr[x][z][7]

                                if (monthlyGLCode.sepGLCode.isEmpty()) {
                                    monthlyGLCode.sepGLCode = glAccountCode;
                                } else {
                                    monthlyGLCode.sepGLCode = monthlyGLCode.sepGLCode + "," + glAccountCode;
                                }

                            } else if (budgetAmountArr[x][z][3] == 10) {
                                monthlyAmount.octAmount = monthlyAmount.octAmount + budgetAmountArr[x][z][4]
                                def incrementedVal = budgetIdWiseBudgetCnt[budgetId]

                                if (incrementedVal != 1) {
                                    monthlyBudgetCount.octItemCount = monthlyBudgetCount.octItemCount + 1
                                }
                                budgetIdWiseBudgetCnt[budgetId] = 1

                                monthlyBudgetId.octBudId = budgetId
                                monthlyBudgetDetailsId.octBudDetId = budgetAmountArr[x][z][7]

                                if (monthlyGLCode.octGLCode.isEmpty()) {
                                    monthlyGLCode.octGLCode = glAccountCode;
                                } else {
                                    monthlyGLCode.octGLCode = monthlyGLCode.octGLCode + "," + glAccountCode;
                                }

                            } else if (budgetAmountArr[x][z][3] == 11) {
                                monthlyAmount.novAmount = monthlyAmount.novAmount + budgetAmountArr[x][z][4]
                                def incrementedVal = budgetIdWiseBudgetCnt[budgetId]
                                if (incrementedVal != 1) {
                                    monthlyBudgetCount.novItemCount = monthlyBudgetCount.novItemCount + 1
                                }
                                budgetIdWiseBudgetCnt[budgetId] = 1

                                monthlyBudgetId.novBudId = budgetId
                                monthlyBudgetDetailsId.novBudDetId = budgetAmountArr[x][z][7]

                                if (monthlyGLCode.novGLCode.isEmpty()) {
                                    monthlyGLCode.novGLCode = glAccountCode;
                                } else {
                                    monthlyGLCode.novGLCode = monthlyGLCode.novGLCode + "," + glAccountCode;
                                }

                            } else if (budgetAmountArr[x][z][3] == 12) {
                                monthlyAmount.decAmount = monthlyAmount.decAmount + budgetAmountArr[x][z][4]
                                def incrementedVal = budgetIdWiseBudgetCnt[budgetId]
                                if (incrementedVal != 1) {
                                    monthlyBudgetCount.decItemCount = monthlyBudgetCount.decItemCount + 1
                                }
                                budgetIdWiseBudgetCnt[budgetId] = 1

                                monthlyBudgetId.decBudId = budgetId
                                monthlyBudgetDetailsId.decBudDetId = budgetAmountArr[x][z][7]

                                if (monthlyGLCode.decGLCode.isEmpty()) {
                                    monthlyGLCode.decGLCode = glAccountCode;
                                } else {
                                    monthlyGLCode.decGLCode = monthlyGLCode.decGLCode + "," + glAccountCode;
                                }
                            }

                        }
                    }
                }

            }

        }

        monthlyBudgetArr.add(monthlyAmount);
        monthlyBudgetArr.add(monthlyBudgetCount);
        monthlyBudgetArr.add(monthlyGLCode);
        monthlyBudgetArr.add(monthlyBudgetId);
        monthlyBudgetArr.add(monthlyBudgetDetailsId);

        return monthlyBudgetArr;
    }

    def List<Map> getMonthlyBudgetAmountAHWise(def glAccountCode, def budgetAmountArr) {

        List monthlyBudgetArr = new ArrayList();
        Map monthlyAmount = ["janAmount": 0, "febAmount": 0, "marAmount": 0, "aprAmount": 0, "mayAmount": 0, "junAmount": 0,
                             "julAmount": 0, "augAmount": 0, "sepAmount": 0, "octAmount": 0, "novAmount": 0, "decAmount": 0];

        Map monthlyBudgetCount = ["janItemCount": 0, "febItemCount": 0, "marItemCount": 0, "aprItemCount": 0, "mayItemCount": 0, "junItemCount": 0,
                                  "julItemCount": 0, "augItemCount": 0, "sepItemCount": 0, "octItemCount": 0, "novItemCount": 0, "decItemCount": 0];

        Map monthlyVenCusId = ["janVenCusId": 0, "febVenCusId": 0, "marVenCusId": 0, "aprVenCusId": 0, "mayVenCusId": 0, "junVenCusId": 0,
                               "julVenCusId": 0, "augVenCusId": 0, "sepVenCusId": 0, "octVenCusId": 0, "novVenCusId": 0, "decVenCusId": 0];

        Map monthlyVenCusName = ["janVenCusName": '', "febVenCusName": '', "marVenCusName": '', "aprVenCusName": '', "mayVenCusName": '', "junVenCusName": '',
                                 "julVenCusName": '', "augVenCusName": '', "sepVenCusName": '', "octVenCusName": '', "novVenCusName": '', "decVenCusName": ''];

        Map monthlyBudgetId = ["janBudId": 0, "febBudId": 0, "marBudId": 0, "aprBudId": 0, "mayBudId": 0, "junBudId": 0,
                               "julBudId": 0, "augBudId": 0, "sepBudId": 0, "octBudId": 0, "novBudId": 0, "decBudId": 0];

        Map monthlyBudgetDetailsId = ["janBudDetId": 0, "febBudDetId": 0, "marBudDetId": 0, "aprBudDetId": 0, "mayBudDetId": 0, "junBudDetId": 0,
                                      "julBudDetId": 0, "augBudDetId": 0, "sepBudDetId": 0, "octBudDetId": 0, "novBudDetId": 0, "decBudDetId": 0];

//        for (int u = 0; u < glAccountArr.size(); u++) {
//            def glAccountCode = glAccountArr[u];
        double summarytotalTempAmount = 0.0
//        def customerId = budgetCustomerIdArr[u];

        for (int x = 0; x < budgetAmountArr.size(); x++) {
            for (int z = 0; z < budgetAmountArr[x].size(); z++) {
                def tmpGlAccountCode = budgetAmountArr[x][z][0];
//                    if (tmpCustomerId == customerId) {
                def venOrCusName = budgetAmountArr[x][z][2];
                if (glAccountCode == tmpGlAccountCode) {

                    if (budgetAmountArr[x][z][3] == 1) {
                        monthlyAmount.janAmount = monthlyAmount.janAmount + budgetAmountArr[x][z][4]
                        monthlyBudgetCount.janItemCount = monthlyBudgetCount.janItemCount + 1

                        monthlyBudgetId.janBudId = budgetAmountArr[x][z][6]
                        monthlyBudgetDetailsId.janBudDetId = budgetAmountArr[x][z][7]

                        monthlyVenCusId.janVenCusId = budgetAmountArr[x][z][1];

                        if (monthlyVenCusName.janVenCusName.isEmpty()) {
                            monthlyVenCusName.janVenCusName = venOrCusName;
                        } else {
                            monthlyVenCusName.janVenCusName = monthlyVenCusName.janVenCusName + "," + venOrCusName;
                        }

                    } else if (budgetAmountArr[x][z][3] == 2) {
                        monthlyAmount.febAmount = monthlyAmount.febAmount + budgetAmountArr[x][z][4]
                        monthlyBudgetCount.febItemCount = monthlyBudgetCount.febItemCount + 1

                        monthlyBudgetId.febBudId = budgetAmountArr[x][z][6]
                        monthlyBudgetDetailsId.febBudDetId = budgetAmountArr[x][z][7]

                        monthlyVenCusId.febVenCusId = budgetAmountArr[x][z][1];
                        if (monthlyVenCusName.febVenCusName.isEmpty()) {
                            monthlyVenCusName.febVenCusName = venOrCusName;
                        } else {
                            monthlyVenCusName.febVenCusName = monthlyVenCusName.febVenCusName + "," + venOrCusName;
                        }
                    } else if (budgetAmountArr[x][z][3] == 3) {
                        monthlyAmount.marAmount = monthlyAmount.marAmount + budgetAmountArr[x][z][4]
                        monthlyBudgetCount.marItemCount = monthlyBudgetCount.marItemCount + 1

                        monthlyBudgetId.marBudId = budgetAmountArr[x][z][6]
                        monthlyBudgetDetailsId.marBudDetId = budgetAmountArr[x][z][7]

                        monthlyVenCusId.marVenCusId = budgetAmountArr[x][z][1];
                        if (monthlyVenCusName.marVenCusName.isEmpty()) {
                            monthlyVenCusName.marVenCusName = venOrCusName;
                        } else {
                            monthlyVenCusName.marVenCusName = monthlyVenCusName.marVenCusName + "," + venOrCusName;
                        }
                    } else if (budgetAmountArr[x][z][3] == 4) {
                        monthlyAmount.aprAmount = monthlyAmount.aprAmount + budgetAmountArr[x][z][4]
                        monthlyBudgetCount.aprItemCount = monthlyBudgetCount.aprItemCount + 1

                        monthlyBudgetId.aprBudId = budgetAmountArr[x][z][6]
                        monthlyBudgetDetailsId.aprBudDetId = budgetAmountArr[x][z][7]

                        monthlyVenCusId.aprVenCusId = budgetAmountArr[x][z][1];
                        if (monthlyVenCusName.aprVenCusName.isEmpty()) {
                            monthlyVenCusName.aprVenCusName = venOrCusName;
                        } else {
                            monthlyVenCusName.aprVenCusName = monthlyVenCusName.aprVenCusName + "," + venOrCusName;
                        }

                    } else if (budgetAmountArr[x][z][3] == 5) {
                        monthlyAmount.mayAmount = monthlyAmount.mayAmount + budgetAmountArr[x][z][4]
                        monthlyBudgetCount.mayItemCount = monthlyBudgetCount.mayItemCount + 1

                        monthlyBudgetId.mayBudId = budgetAmountArr[x][z][6]
                        monthlyBudgetDetailsId.mayBudDetId = budgetAmountArr[x][z][7]

                        monthlyVenCusId.mayVenCusId = budgetAmountArr[x][z][1];
                        if (monthlyVenCusName.mayVenCusName.isEmpty()) {
                            monthlyVenCusName.mayVenCusName = venOrCusName;
                        } else {
                            monthlyVenCusName.mayVenCusName = monthlyVenCusName.mayVenCusName + "," + venOrCusName;
                        }

                    } else if (budgetAmountArr[x][z][3] == 6) {
                        monthlyAmount.junAmount = monthlyAmount.junAmount + budgetAmountArr[x][z][4]
                        monthlyBudgetCount.junItemCount = monthlyBudgetCount.junItemCount + 1

                        monthlyBudgetId.junBudId = budgetAmountArr[x][z][6]
                        monthlyBudgetDetailsId.junBudDetId = budgetAmountArr[x][z][7]

                        monthlyVenCusId.junVenCusId = budgetAmountArr[x][z][1];
                        if (monthlyVenCusName.junVenCusName.isEmpty()) {
                            monthlyVenCusName.junVenCusName = venOrCusName;
                        } else {
                            monthlyVenCusName.junVenCusName = monthlyVenCusName.junVenCusName + "," + venOrCusName;
                        }

                    } else if (budgetAmountArr[x][z][3] == 7) {
                        monthlyAmount.julAmount = monthlyAmount.julAmount + budgetAmountArr[x][z][4]
                        monthlyBudgetCount.julItemCount = monthlyBudgetCount.julItemCount + 1

                        monthlyBudgetId.julBudId = budgetAmountArr[x][z][6]
                        monthlyBudgetDetailsId.julBudDetId = budgetAmountArr[x][z][6]

                        monthlyVenCusId.julVenCusId = budgetAmountArr[x][z][1];
                        if (monthlyVenCusName.julVenCusName.isEmpty()) {
                            monthlyVenCusName.julVenCusName = venOrCusName;
                        } else {
                            monthlyVenCusName.julVenCusName = monthlyVenCusName.julVenCusName + "," + venOrCusName;
                        }
                    } else if (budgetAmountArr[x][z][3] == 8) {
                        monthlyAmount.augAmount = monthlyAmount.augAmount + budgetAmountArr[x][z][4]
                        monthlyBudgetCount.augItemCount = monthlyBudgetCount.augItemCount + 1

                        monthlyBudgetId.augBudId = budgetAmountArr[x][z][6]
                        monthlyBudgetDetailsId.augBudDetId = budgetAmountArr[x][z][7]

                        monthlyVenCusId.augVenCusId = budgetAmountArr[x][z][1];
                        if (monthlyVenCusName.augVenCusName.isEmpty()) {
                            monthlyVenCusName.augVenCusName = venOrCusName;
                        } else {
                            monthlyVenCusName.augVenCusName = monthlyVenCusName.augVenCusName + "," + venOrCusName;
                        }

                    } else if (budgetAmountArr[x][z][3] == 9) {
                        monthlyAmount.sepAmount = monthlyAmount.sepAmount + budgetAmountArr[x][z][4]
                        monthlyBudgetCount.sepItemCount = monthlyBudgetCount.sepItemCount + 1

                        monthlyBudgetId.sepBudId = budgetAmountArr[x][z][6]
                        monthlyBudgetDetailsId.sepBudDetId = budgetAmountArr[x][z][7]

                        monthlyVenCusId.sepVenCusId = budgetAmountArr[x][z][1];
                        if (monthlyVenCusName.sepVenCusName.isEmpty()) {
                            monthlyVenCusName.sepVenCusName = venOrCusName;
                        } else {
                            monthlyVenCusName.sepVenCusName = monthlyVenCusName.sepVenCusName + "," + venOrCusName;
                        }

                    } else if (budgetAmountArr[x][z][3] == 10) {
                        monthlyAmount.octAmount = monthlyAmount.octAmount + budgetAmountArr[x][z][4]
                        monthlyBudgetCount.octItemCount = monthlyBudgetCount.octItemCount + 1

                        monthlyBudgetId.octBudId = budgetAmountArr[x][z][6]
                        monthlyBudgetDetailsId.octBudDetId = budgetAmountArr[x][z][7]

                        monthlyVenCusId.octVenCusId = budgetAmountArr[x][z][1];
                        if (monthlyVenCusName.octVenCusName.isEmpty()) {
                            monthlyVenCusName.octVenCusName = venOrCusName;
                        } else {
                            monthlyVenCusName.octVenCusName = monthlyVenCusName.octVenCusName + "," + venOrCusName;
                        }

                    } else if (budgetAmountArr[x][z][3] == 11) {
                        monthlyAmount.novAmount = monthlyAmount.novAmount + budgetAmountArr[x][z][4]
                        monthlyBudgetCount.novItemCount = monthlyBudgetCount.novItemCount + 1

                        monthlyBudgetId.novBudId = budgetAmountArr[x][z][6]
                        monthlyBudgetDetailsId.novBudDetId = budgetAmountArr[x][z][7]

                        monthlyVenCusId.novVenCusId = budgetAmountArr[x][z][1];
                        if (monthlyVenCusName.novVenCusName.isEmpty()) {
                            monthlyVenCusName.novVenCusName = venOrCusName;
                        } else {
                            monthlyVenCusName.novVenCusName = monthlyVenCusName.novVenCusName + "," + venOrCusName;
                        }

                    } else if (budgetAmountArr[x][z][3] == 12) {
                        monthlyAmount.decAmount = monthlyAmount.decAmount + budgetAmountArr[x][z][4]
                        monthlyBudgetCount.decItemCount = monthlyBudgetCount.decItemCount + 1

                        monthlyBudgetId.decBudId = budgetAmountArr[x][z][6]
                        monthlyBudgetDetailsId.decBudDetId = budgetAmountArr[x][z][7]

                        monthlyVenCusId.decVenCusId = budgetAmountArr[x][z][1];
                        if (monthlyVenCusName.decVenCusName.isEmpty()) {
                            monthlyVenCusName.decVenCusName = venOrCusName;
                        } else {
                            monthlyVenCusName.decVenCusName = monthlyVenCusName.decVenCusName + "," + venOrCusName;
                        }
                    }

                }
//                    }
            }

        }

//        }

        monthlyBudgetArr.add(monthlyAmount);
        monthlyBudgetArr.add(monthlyBudgetCount);
        monthlyBudgetArr.add(monthlyVenCusName);
        monthlyBudgetArr.add(monthlyBudgetId);
        monthlyBudgetArr.add(monthlyBudgetDetailsId);
        monthlyBudgetArr.add(monthlyVenCusId);

        return monthlyBudgetArr;
    }


    def List<Map> getMonthlyReservationBudgetAmountAHWise(def budgetName,def budgetAmountArr) {

        List monthlyBudgetArr = new ArrayList();
        Map monthlyAmount = ["janAmount": 0,"febAmount": 0,"marAmount": 0,"aprAmount": 0,"mayAmount": 0,"junAmount": 0,
                             "julAmount": 0,"augAmount": 0,"sepAmount": 0,"octAmount": 0,"novAmount": 0,"decAmount": 0];

        Map monthlyBudgetCount = ["janItemCount": 0,"febItemCount": 0,"marItemCount": 0,"aprItemCount": 0,"mayItemCount": 0,"junItemCount": 0,
                                  "julItemCount": 0,"augItemCount": 0,"sepItemCount": 0,"octItemCount": 0,"novItemCount": 0,"decItemCount": 0];

        Map monthlyVenCusId = ["janVenCusId": 0,"febVenCusId": 0,"marVenCusId": 0,"aprVenCusId": 0,"mayVenCusId": 0,"junVenCusId": 0,
                               "julVenCusId": 0,"augVenCusId": 0,"sepVenCusId": 0,"octVenCusId": 0,"novVenCusId": 0,"decVenCusId": 0];

        Map monthlyVenCusName = ["janVenCusName": '',"febVenCusName": '',"marVenCusName": '',"aprVenCusName": '',"mayVenCusName": '',"junVenCusName": '',
                                 "julVenCusName": '',"augVenCusName": '',"sepVenCusName": '',"octVenCusName": '',"novVenCusName": '',"decVenCusName": ''];

        Map monthlyBudgetId = ["janBudId": 0,"febBudId": 0,"marBudId": 0,"aprBudId": 0,"mayBudId": 0,"junBudId": 0,
                               "julBudId": 0,"augBudId": 0,"sepBudId": 0,"octBudId": 0,"novBudId": 0,"decBudId": 0];

        Map monthlyBudgetDetailsId = ["janBudDetId": 0,"febBudDetId": 0,"marBudDetId": 0,"aprBudDetId": 0,"mayBudDetId": 0,"junBudDetId": 0,
                                      "julBudDetId": 0,"augBudDetId": 0,"sepBudDetId": 0,"octBudDetId": 0,"novBudDetId": 0,"decBudDetId": 0];

//        for (int u = 0; u < glAccountArr.size(); u++) {
//            def glAccountCode = glAccountArr[u];
        double summarytotalTempAmount = 0.0
//        def customerId = budgetCustomerIdArr[u];

        for (int x = 0; x < budgetAmountArr.size(); x++) {
            for (int z = 0; z < budgetAmountArr[x].size(); z++) {
                def tmpGlAccountCode= budgetAmountArr[x][z][0];
//                def tmpGlAccountCode= budgetAmountArr[x][z][1];
//                    if (tmpCustomerId == customerId) {
                def venOrCusName = budgetAmountArr[x][z][2];
                if (budgetName == tmpGlAccountCode) {

                    if (budgetAmountArr[x][z][3] == 1) {
                        monthlyAmount.janAmount = monthlyAmount.janAmount + budgetAmountArr[x][z][4]
                        monthlyBudgetCount.janItemCount = monthlyBudgetCount.janItemCount + 1

                        monthlyBudgetId.janBudId = budgetAmountArr[x][z][6]
                        monthlyBudgetDetailsId.janBudDetId = budgetAmountArr[x][z][7]

                        monthlyVenCusId.janVenCusId = budgetAmountArr[x][z][1];

                        if(monthlyVenCusName.janVenCusName.isEmpty()){
                            monthlyVenCusName.janVenCusName = venOrCusName;
                        }
                        else{
                            monthlyVenCusName.janVenCusName = monthlyVenCusName.janVenCusName + "," + venOrCusName ;
                        }

                    } else if (budgetAmountArr[x][z][3] == 2) {
                        monthlyAmount.febAmount = monthlyAmount.febAmount + budgetAmountArr[x][z][4]
                        monthlyBudgetCount.febItemCount = monthlyBudgetCount.febItemCount + 1

                        monthlyBudgetId.febBudId = budgetAmountArr[x][z][6]
                        monthlyBudgetDetailsId.febBudDetId = budgetAmountArr[x][z][7]

                        monthlyVenCusId.febVenCusId = budgetAmountArr[x][z][1];
                        if(monthlyVenCusName.febVenCusName.isEmpty()){
                            monthlyVenCusName.febVenCusName = venOrCusName;
                        }
                        else{
                            monthlyVenCusName.febVenCusName = monthlyVenCusName.febVenCusName + "," + venOrCusName ;
                        }
                    } else if (budgetAmountArr[x][z][3] == 3) {
                        monthlyAmount.marAmount = monthlyAmount.marAmount + budgetAmountArr[x][z][4]
                        monthlyBudgetCount.marItemCount = monthlyBudgetCount.marItemCount + 1

                        monthlyBudgetId.marBudId = budgetAmountArr[x][z][6]
                        monthlyBudgetDetailsId.marBudDetId = budgetAmountArr[x][z][7]

                        monthlyVenCusId.marVenCusId = budgetAmountArr[x][z][1];
                        if(monthlyVenCusName.marVenCusName.isEmpty()){
                            monthlyVenCusName.marVenCusName = venOrCusName;
                        }
                        else{
                            monthlyVenCusName.marVenCusName = monthlyVenCusName.marVenCusName + "," + venOrCusName ;
                        }
                    } else if (budgetAmountArr[x][z][3] == 4) {
                        monthlyAmount.aprAmount = monthlyAmount.aprAmount + budgetAmountArr[x][z][4]
                        monthlyBudgetCount.aprItemCount = monthlyBudgetCount.aprItemCount + 1

                        monthlyBudgetId.aprBudId = budgetAmountArr[x][z][6]
                        monthlyBudgetDetailsId.aprBudDetId = budgetAmountArr[x][z][7]

                        monthlyVenCusId.aprVenCusId = budgetAmountArr[x][z][1];
                        if(monthlyVenCusName.aprVenCusName.isEmpty()){
                            monthlyVenCusName.aprVenCusName = venOrCusName;
                        }
                        else{
                            monthlyVenCusName.aprVenCusName = monthlyVenCusName.aprVenCusName + "," + venOrCusName ;
                        }

                    } else if (budgetAmountArr[x][z][3] == 5) {
                        monthlyAmount.mayAmount = monthlyAmount.mayAmount + budgetAmountArr[x][z][4]
                        monthlyBudgetCount.mayItemCount = monthlyBudgetCount.mayItemCount + 1

                        monthlyBudgetId.mayBudId = budgetAmountArr[x][z][6]
                        monthlyBudgetDetailsId.mayBudDetId = budgetAmountArr[x][z][7]

                        monthlyVenCusId.mayVenCusId = budgetAmountArr[x][z][1];
                        if(monthlyVenCusName.mayVenCusName.isEmpty()){
                            monthlyVenCusName.mayVenCusName = venOrCusName;
                        }
                        else{
                            monthlyVenCusName.mayVenCusName = monthlyVenCusName.mayVenCusName + "," + venOrCusName ;
                        }

                    } else if (budgetAmountArr[x][z][3] == 6) {
                        monthlyAmount.junAmount = monthlyAmount.junAmount + budgetAmountArr[x][z][4]
                        monthlyBudgetCount.junItemCount = monthlyBudgetCount.junItemCount + 1

                        monthlyBudgetId.junBudId = budgetAmountArr[x][z][6]
                        monthlyBudgetDetailsId.junBudDetId = budgetAmountArr[x][z][7]

                        monthlyVenCusId.junVenCusId = budgetAmountArr[x][z][1];
                        if(monthlyVenCusName.junVenCusName.isEmpty()){
                            monthlyVenCusName.junVenCusName = venOrCusName;
                        }
                        else{
                            monthlyVenCusName.junVenCusName = monthlyVenCusName.junVenCusName + "," + venOrCusName ;
                        }

                    } else if (budgetAmountArr[x][z][3] == 7) {
                        monthlyAmount.julAmount = monthlyAmount.julAmount + budgetAmountArr[x][z][4]
                        monthlyBudgetCount.julItemCount = monthlyBudgetCount.julItemCount + 1

                        monthlyBudgetId.julBudId = budgetAmountArr[x][z][6]
                        monthlyBudgetDetailsId.julBudDetId = budgetAmountArr[x][z][6]

                        monthlyVenCusId.julVenCusId = budgetAmountArr[x][z][1];
                        if(monthlyVenCusName.julVenCusName.isEmpty()){
                            monthlyVenCusName.julVenCusName = venOrCusName;
                        }
                        else{
                            monthlyVenCusName.julVenCusName = monthlyVenCusName.julVenCusName + "," + venOrCusName ;
                        }
                    } else if (budgetAmountArr[x][z][3] == 8) {
                        monthlyAmount.augAmount = monthlyAmount.augAmount + budgetAmountArr[x][z][4]
                        monthlyBudgetCount.augItemCount = monthlyBudgetCount.augItemCount + 1

                        monthlyBudgetId.augBudId = budgetAmountArr[x][z][6]
                        monthlyBudgetDetailsId.augBudDetId = budgetAmountArr[x][z][7]

                        monthlyVenCusId.augVenCusId = budgetAmountArr[x][z][1];
                        if(monthlyVenCusName.augVenCusName.isEmpty()){
                            monthlyVenCusName.augVenCusName = venOrCusName;
                        }
                        else{
                            monthlyVenCusName.augVenCusName = monthlyVenCusName.augVenCusName + "," + venOrCusName ;
                        }

                    } else if (budgetAmountArr[x][z][3] == 9) {
                        monthlyAmount.sepAmount = monthlyAmount.sepAmount + budgetAmountArr[x][z][4]
                        monthlyBudgetCount.sepItemCount = monthlyBudgetCount.sepItemCount + 1

                        monthlyBudgetId.sepBudId = budgetAmountArr[x][z][6]
                        monthlyBudgetDetailsId.sepBudDetId = budgetAmountArr[x][z][7]

                        monthlyVenCusId.sepVenCusId = budgetAmountArr[x][z][1];
                        if(monthlyVenCusName.sepVenCusName.isEmpty()){
                            monthlyVenCusName.sepVenCusName = venOrCusName;
                        }
                        else{
                            monthlyVenCusName.sepVenCusName = monthlyVenCusName.sepVenCusName + "," + venOrCusName ;
                        }

                    } else if (budgetAmountArr[x][z][3] == 10) {
                        monthlyAmount.octAmount = monthlyAmount.octAmount + budgetAmountArr[x][z][4]
                        monthlyBudgetCount.octItemCount = monthlyBudgetCount.octItemCount + 1

                        monthlyBudgetId.octBudId = budgetAmountArr[x][z][6]
                        monthlyBudgetDetailsId.octBudDetId = budgetAmountArr[x][z][7]

                        monthlyVenCusId.octVenCusId = budgetAmountArr[x][z][1];
                        if(monthlyVenCusName.octVenCusName.isEmpty()){
                            monthlyVenCusName.octVenCusName = venOrCusName;
                        }
                        else{
                            monthlyVenCusName.octVenCusName = monthlyVenCusName.octVenCusName + "," + venOrCusName ;
                        }

                    } else if (budgetAmountArr[x][z][3] == 11) {
                        monthlyAmount.novAmount = monthlyAmount.novAmount + budgetAmountArr[x][z][4]
                        monthlyBudgetCount.novItemCount = monthlyBudgetCount.novItemCount + 1

                        monthlyBudgetId.novBudId = budgetAmountArr[x][z][6]
                        monthlyBudgetDetailsId.novBudDetId = budgetAmountArr[x][z][7]

                        monthlyVenCusId.novVenCusId = budgetAmountArr[x][z][1];
                        if(monthlyVenCusName.novVenCusName.isEmpty()){
                            monthlyVenCusName.novVenCusName = venOrCusName;
                        }
                        else{
                            monthlyVenCusName.novVenCusName = monthlyVenCusName.novVenCusName + "," + venOrCusName ;
                        }

                    } else if (budgetAmountArr[x][z][3] == 12) {
                        monthlyAmount.decAmount = monthlyAmount.decAmount + budgetAmountArr[x][z][4]
                        monthlyBudgetCount.decItemCount = monthlyBudgetCount.decItemCount + 1

                        monthlyBudgetId.decBudId = budgetAmountArr[x][z][6]
                        monthlyBudgetDetailsId.decBudDetId = budgetAmountArr[x][z][7]

                        monthlyVenCusId.decVenCusId = budgetAmountArr[x][z][1];
                        if(monthlyVenCusName.decVenCusName.isEmpty()){
                            monthlyVenCusName.decVenCusName = venOrCusName;
                        }
                        else{
                            monthlyVenCusName.decVenCusName = monthlyVenCusName.decVenCusName + "," + venOrCusName ;
                        }
                    }

                }
//                    }
            }

        }

//        }

        monthlyBudgetArr.add(monthlyAmount);
        monthlyBudgetArr.add(monthlyBudgetCount);
        monthlyBudgetArr.add(monthlyVenCusName);
        monthlyBudgetArr.add(monthlyBudgetId);
        monthlyBudgetArr.add(monthlyBudgetDetailsId);
        monthlyBudgetArr.add(monthlyVenCusId);

        return monthlyBudgetArr;
    }



    def List<Map> getMonthlyInvoiceAmountAHWise(def glAccountCode, def invoiceAmountArr, boolean bParseNeed) {
        List monthlyInvoiceArr = new ArrayList();

        Map monthlyAmount = ["janAmount": 0, "febAmount": 0, "marAmount": 0, "aprAmount": 0, "mayAmount": 0, "junAmount": 0,
                             "julAmount": 0, "augAmount": 0, "sepAmount": 0, "octAmount": 0, "novAmount": 0, "decAmount": 0];

        Map monthlyInvoiceCount = ["janItemCount": 0, "febItemCount": 0, "marItemCount": 0, "aprItemCount": 0, "mayItemCount": 0, "junItemCount": 0,
                                   "julItemCount": 0, "augItemCount": 0, "sepItemCount": 0, "octItemCount": 0, "novItemCount": 0, "decItemCount": 0];

//        for (int u = 0; u < glAccountArr.size(); u++) {
        double summarytotalTempAmount = 0.0

//            def glAccountCode = glAccountArr[u];
//            def customerId = budgetCustomerIdArr[u];
//            if(bParseNeed){
//                customerId = Integer.parseInt(budgetCustomerIdArr[u]);
//            }

        for (int x = 0; x < invoiceAmountArr.size(); x++) {
            if (invoiceAmountArr[x].size() > 0) {
                for (int z = 0; z < invoiceAmountArr[x].size(); z++) {
//                        def tmpCustomerId = invoiceAmountArr[x][z][0];
                    def tmpGlCode = invoiceAmountArr[x][z][0];

//                        if (tmpCustomerId == customerId) {
                    if (glAccountCode == tmpGlCode) {
                        if (Integer.parseInt(invoiceAmountArr[x][z][3]) == 1) {
                            monthlyAmount.janAmount = monthlyAmount.janAmount + invoiceAmountArr[x][z][4]
                            monthlyInvoiceCount.janItemCount = monthlyInvoiceCount.janItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][3]) == 2) {
                            monthlyAmount.febAmount = monthlyAmount.febAmount + invoiceAmountArr[x][z][4]
                            monthlyInvoiceCount.febItemCount = monthlyInvoiceCount.febItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][3]) == 3) {
                            monthlyAmount.marAmount = monthlyAmount.marAmount + invoiceAmountArr[x][z][4]
                            monthlyInvoiceCount.marItemCount = monthlyInvoiceCount.marItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][3]) == 4) {
                            monthlyAmount.aprAmount = monthlyAmount.aprAmount + invoiceAmountArr[x][z][4]
                            monthlyInvoiceCount.aprItemCount = monthlyInvoiceCount.aprItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][3]) == 5) {
                            monthlyAmount.mayAmount = monthlyAmount.mayAmount + invoiceAmountArr[x][z][4]
                            monthlyInvoiceCount.mayItemCount = monthlyInvoiceCount.mayItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][3]) == 6) {
                            monthlyAmount.junAmount = monthlyAmount.junAmount + invoiceAmountArr[x][z][4]
                            monthlyInvoiceCount.junItemCount = monthlyInvoiceCount.junItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][3]) == 7) {
                            monthlyAmount.julAmount = monthlyAmount.julAmount + invoiceAmountArr[x][z][4]
                            monthlyInvoiceCount.julItemCount = monthlyInvoiceCount.julItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][3]) == 8) {
                            monthlyAmount.augAmount = monthlyAmount.augAmount + invoiceAmountArr[x][z][4]
                            monthlyInvoiceCount.augItemCount = monthlyInvoiceCount.augItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][3]) == 9) {
                            monthlyAmount.sepAmount = monthlyAmount.sepAmount + invoiceAmountArr[x][z][4]
                            monthlyInvoiceCount.sepItemCount = monthlyInvoiceCount.sepItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][3]) == 10) {
                            monthlyAmount.octAmount = monthlyAmount.octAmount + invoiceAmountArr[x][z][4]
                            monthlyInvoiceCount.octItemCount = monthlyInvoiceCount.octItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][3]) == 11) {
                            monthlyAmount.novAmount = monthlyAmount.novAmount + invoiceAmountArr[x][z][4]
                            monthlyInvoiceCount.novItemCount = monthlyInvoiceCount.novItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][3]) == 12) {
                            monthlyAmount.decAmount = monthlyAmount.decAmount + invoiceAmountArr[x][z][4]
                            monthlyInvoiceCount.decItemCount = monthlyInvoiceCount.decItemCount + 1
                        }
                    }
//                        }
                }
            }
        }

        //}

        monthlyInvoiceArr.add(monthlyAmount);
        monthlyInvoiceArr.add(monthlyInvoiceCount);

        return monthlyInvoiceArr;
    }


    def List<Map> getMonthlyReservationInvoiceAmountAHWise(def glAccountArr,def budgetNameId, def invoiceAmountArr,boolean bParseNeed) {

        List monthlyInvoiceArr = new ArrayList();
        Map monthlyAmount = ["janAmount": 0,"febAmount": 0,"marAmount": 0,"aprAmount": 0,"mayAmount": 0,"junAmount": 0,
                             "julAmount": 0,"augAmount": 0,"sepAmount": 0,"octAmount": 0,"novAmount": 0,"decAmount": 0];

        Map monthlyInvoiceCount = ["janItemCount": 0,"febItemCount": 0,"marItemCount": 0,"aprItemCount": 0,"mayItemCount": 0,"junItemCount": 0,
                                   "julItemCount": 0,"augItemCount": 0,"sepItemCount": 0,"octItemCount": 0,"novItemCount": 0,"decItemCount": 0];

        int  u = 0;
//        for (int u = 0; u < glAccountArr.size(); u++) {
        double summarytotalTempAmount = 0.0

//            def glAccountCode = glAccountArr[u];
        def customerId = budgetNameId
        if(bParseNeed){
            customerId = Integer.parseInt(budgetNameId.toString());
        }

        for (int x = 0; x < invoiceAmountArr.size(); x++) {

            if (invoiceAmountArr[x].size() > 0) {
                for (int z = 0; z < invoiceAmountArr[x].size(); z++) {
                    def tmpCustomerId = invoiceAmountArr[x][z][0];

//                        def tmpGlCode = invoiceAmountArr[x][z][1];

                    if (tmpCustomerId == customerId) {

//                            if (Integer.parseInt(glAccountCode) == Integer.parseInt(tmpGlCode)) {
                        if (Integer.parseInt(invoiceAmountArr[x][z][2].toString()) == 1) {
                            monthlyAmount.janAmount = monthlyAmount.janAmount + invoiceAmountArr[x][z][3]
                            monthlyInvoiceCount.janItemCount = monthlyInvoiceCount.janItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][2].toString()) == 2) {
                            monthlyAmount.febAmount = monthlyAmount.febAmount + invoiceAmountArr[x][z][3]
                            monthlyInvoiceCount.febItemCount = monthlyInvoiceCount.febItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][2].toString()) == 3) {
                            monthlyAmount.marAmount = monthlyAmount.marAmount + invoiceAmountArr[x][z][3]
                            monthlyInvoiceCount.marItemCount = monthlyInvoiceCount.marItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][2].toString()) == 4) {
                            monthlyAmount.aprAmount = monthlyAmount.aprAmount + invoiceAmountArr[x][z][3]
                            monthlyInvoiceCount.aprItemCount = monthlyInvoiceCount.aprItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][2].toString()) == 5) {
                            monthlyAmount.mayAmount = monthlyAmount.mayAmount + invoiceAmountArr[x][z][3]
                            monthlyInvoiceCount.mayItemCount = monthlyInvoiceCount.mayItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][2].toString()) == 6) {
                            monthlyAmount.junAmount = monthlyAmount.junAmount + invoiceAmountArr[x][z][3]
                            monthlyInvoiceCount.junItemCount = monthlyInvoiceCount.junItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][2].toString()) == 7) {
                            monthlyAmount.julAmount = monthlyAmount.julAmount + invoiceAmountArr[x][z][3]
                            monthlyInvoiceCount.julItemCount = monthlyInvoiceCount.julItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][2].toString()) == 8) {
                            monthlyAmount.augAmount = monthlyAmount.augAmount + invoiceAmountArr[x][z][3]
                            monthlyInvoiceCount.augItemCount = monthlyInvoiceCount.augItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][2].toString()) == 9) {
                            monthlyAmount.sepAmount = monthlyAmount.sepAmount + invoiceAmountArr[x][z][3]
                            monthlyInvoiceCount.sepItemCount = monthlyInvoiceCount.sepItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][2].toString()) == 10) {
                            monthlyAmount.octAmount = monthlyAmount.octAmount + invoiceAmountArr[x][z][3]
                            monthlyInvoiceCount.octItemCount = monthlyInvoiceCount.octItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][2].toString()) == 11) {
                            monthlyAmount.novAmount = monthlyAmount.novAmount + invoiceAmountArr[x][z][3]
                            monthlyInvoiceCount.novItemCount = monthlyInvoiceCount.novItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][2].toString()) == 12) {
                            monthlyAmount.decAmount = monthlyAmount.decAmount + invoiceAmountArr[x][z][3]
                            monthlyInvoiceCount.decItemCount = monthlyInvoiceCount.decItemCount + 1
                        }
//                            }
                    }
                }
            }
        }

//        }

        monthlyInvoiceArr.add(monthlyAmount);
        monthlyInvoiceArr.add(monthlyInvoiceCount);

        return monthlyInvoiceArr;

    }


    def List<Map> getMonthlyInvoiceAmount(def glAccountArr, def budgetCustomerIdArr, def invoiceAmountArr, boolean bParseNeed) {

        List monthlyInvoiceArr = new ArrayList();
        Map monthlyAmount = ["janAmount": 0, "febAmount": 0, "marAmount": 0, "aprAmount": 0, "mayAmount": 0, "junAmount": 0,
                             "julAmount": 0, "augAmount": 0, "sepAmount": 0, "octAmount": 0, "novAmount": 0, "decAmount": 0];

        Map monthlyInvoiceCount = ["janItemCount": 0, "febItemCount": 0, "marItemCount": 0, "aprItemCount": 0, "mayItemCount": 0, "junItemCount": 0,
                                   "julItemCount": 0, "augItemCount": 0, "sepItemCount": 0, "octItemCount": 0, "novItemCount": 0, "decItemCount": 0];

        int u = 0;
//        for (int u = 0; u < glAccountArr.size(); u++) {
        double summarytotalTempAmount = 0.0

//            def glAccountCode = glAccountArr[u];
        def customerId = budgetCustomerIdArr[u];
        if (bParseNeed) {
            customerId = Integer.parseInt(budgetCustomerIdArr[u]);
        }

        for (int x = 0; x < invoiceAmountArr.size(); x++) {

            if (invoiceAmountArr[x].size() > 0) {
                for (int z = 0; z < invoiceAmountArr[x].size(); z++) {
                    def tmpCustomerId = invoiceAmountArr[x][z][0];

//                        def tmpGlCode = invoiceAmountArr[x][z][1];

                    if (tmpCustomerId == customerId) {
//                            if (Integer.parseInt(glAccountCode) == Integer.parseInt(tmpGlCode)) {
                        if (Integer.parseInt(invoiceAmountArr[x][z][3]) == 1) {
                            monthlyAmount.janAmount = monthlyAmount.janAmount + invoiceAmountArr[x][z][4]
                            monthlyInvoiceCount.janItemCount = monthlyInvoiceCount.janItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][3]) == 2) {
                            monthlyAmount.febAmount = monthlyAmount.febAmount + invoiceAmountArr[x][z][4]
                            monthlyInvoiceCount.febItemCount = monthlyInvoiceCount.febItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][3]) == 3) {
                            monthlyAmount.marAmount = monthlyAmount.marAmount + invoiceAmountArr[x][z][4]
                            monthlyInvoiceCount.marItemCount = monthlyInvoiceCount.marItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][3]) == 4) {
                            monthlyAmount.aprAmount = monthlyAmount.aprAmount + invoiceAmountArr[x][z][4]
                            monthlyInvoiceCount.aprItemCount = monthlyInvoiceCount.aprItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][3]) == 5) {
                            monthlyAmount.mayAmount = monthlyAmount.mayAmount + invoiceAmountArr[x][z][4]
                            monthlyInvoiceCount.mayItemCount = monthlyInvoiceCount.mayItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][3]) == 6) {
                            monthlyAmount.junAmount = monthlyAmount.junAmount + invoiceAmountArr[x][z][4]
                            monthlyInvoiceCount.junItemCount = monthlyInvoiceCount.junItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][3]) == 7) {
                            monthlyAmount.julAmount = monthlyAmount.julAmount + invoiceAmountArr[x][z][4]
                            monthlyInvoiceCount.julItemCount = monthlyInvoiceCount.julItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][3]) == 8) {
                            monthlyAmount.augAmount = monthlyAmount.augAmount + invoiceAmountArr[x][z][4]
                            monthlyInvoiceCount.augItemCount = monthlyInvoiceCount.augItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][3]) == 9) {
                            monthlyAmount.sepAmount = monthlyAmount.sepAmount + invoiceAmountArr[x][z][4]
                            monthlyInvoiceCount.sepItemCount = monthlyInvoiceCount.sepItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][3]) == 10) {
                            monthlyAmount.octAmount = monthlyAmount.octAmount + invoiceAmountArr[x][z][4]
                            monthlyInvoiceCount.octItemCount = monthlyInvoiceCount.octItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][3]) == 11) {
                            monthlyAmount.novAmount = monthlyAmount.novAmount + invoiceAmountArr[x][z][4]
                            monthlyInvoiceCount.novItemCount = monthlyInvoiceCount.novItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][3]) == 12) {
                            monthlyAmount.decAmount = monthlyAmount.decAmount + invoiceAmountArr[x][z][4]
                            monthlyInvoiceCount.decItemCount = monthlyInvoiceCount.decItemCount + 1
                        }
//                            }
                    }
                }
            }
        }

//        }

        monthlyInvoiceArr.add(monthlyAmount);
        monthlyInvoiceArr.add(monthlyInvoiceCount);

        return monthlyInvoiceArr;
    }


    def List<Map> getPrivateReservationMonthlyInvoiceAmount(def glAccountArr,def budgetCustomerIdArr, def invoiceAmountArr,boolean bParseNeed) {

        List monthlyInvoiceArr = new ArrayList();
        Map monthlyAmount = ["janAmount": 0,"febAmount": 0,"marAmount": 0,"aprAmount": 0,"mayAmount": 0,"junAmount": 0,
                             "julAmount": 0,"augAmount": 0,"sepAmount": 0,"octAmount": 0,"novAmount": 0,"decAmount": 0];

        Map monthlyInvoiceCount = ["janItemCount": 0,"febItemCount": 0,"marItemCount": 0,"aprItemCount": 0,"mayItemCount": 0,"junItemCount": 0,
                                   "julItemCount": 0,"augItemCount": 0,"sepItemCount": 0,"octItemCount": 0,"novItemCount": 0,"decItemCount": 0];

        int  u = 0;
//        for (int u = 0; u < glAccountArr.size(); u++) {
        double summarytotalTempAmount = 0.0

//            def glAccountCode = glAccountArr[u];
        def customerId = budgetCustomerIdArr[u];
        if(bParseNeed){
            customerId = Integer.parseInt(budgetCustomerIdArr[u].toString());
        }

        for (int x = 0; x < invoiceAmountArr.size(); x++) {

            if (invoiceAmountArr[x].size() > 0) {
                for (int z = 0; z < invoiceAmountArr[x].size(); z++) {
                    def tmpCustomerId = invoiceAmountArr[x][z][0];

//                        def tmpGlCode = invoiceAmountArr[x][z][1];

                    if (tmpCustomerId == customerId) {

//                            if (Integer.parseInt(glAccountCode) == Integer.parseInt(tmpGlCode)) {
                        if (Integer.parseInt(invoiceAmountArr[x][z][2].toString()) == 1) {
                            monthlyAmount.janAmount = monthlyAmount.janAmount + invoiceAmountArr[x][z][3]
                            monthlyInvoiceCount.janItemCount = monthlyInvoiceCount.janItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][2].toString()) == 2) {
                            monthlyAmount.febAmount = monthlyAmount.febAmount + invoiceAmountArr[x][z][3]
                            monthlyInvoiceCount.febItemCount = monthlyInvoiceCount.febItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][2].toString()) == 3) {
                            monthlyAmount.marAmount = monthlyAmount.marAmount + invoiceAmountArr[x][z][3]
                            monthlyInvoiceCount.marItemCount = monthlyInvoiceCount.marItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][2].toString()) == 4) {
                            monthlyAmount.aprAmount = monthlyAmount.aprAmount + invoiceAmountArr[x][z][3]
                            monthlyInvoiceCount.aprItemCount = monthlyInvoiceCount.aprItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][2].toString()) == 5) {
                            monthlyAmount.mayAmount = monthlyAmount.mayAmount + invoiceAmountArr[x][z][3]
                            monthlyInvoiceCount.mayItemCount = monthlyInvoiceCount.mayItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][2].toString()) == 6) {
                            monthlyAmount.junAmount = monthlyAmount.junAmount + invoiceAmountArr[x][z][3]
                            monthlyInvoiceCount.junItemCount = monthlyInvoiceCount.junItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][2].toString()) == 7) {
                            monthlyAmount.julAmount = monthlyAmount.julAmount + invoiceAmountArr[x][z][3]
                            monthlyInvoiceCount.julItemCount = monthlyInvoiceCount.julItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][2].toString()) == 8) {
                            monthlyAmount.augAmount = monthlyAmount.augAmount + invoiceAmountArr[x][z][3]
                            monthlyInvoiceCount.augItemCount = monthlyInvoiceCount.augItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][2].toString()) == 9) {
                            monthlyAmount.sepAmount = monthlyAmount.sepAmount + invoiceAmountArr[x][z][3]
                            monthlyInvoiceCount.sepItemCount = monthlyInvoiceCount.sepItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][2].toString()) == 10) {
                            monthlyAmount.octAmount = monthlyAmount.octAmount + invoiceAmountArr[x][z][3]
                            monthlyInvoiceCount.octItemCount = monthlyInvoiceCount.octItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][2].toString()) == 11) {
                            monthlyAmount.novAmount = monthlyAmount.novAmount + invoiceAmountArr[x][z][3]
                            monthlyInvoiceCount.novItemCount = monthlyInvoiceCount.novItemCount + 1
                        } else if (Integer.parseInt(invoiceAmountArr[x][z][2].toString()) == 12) {
                            monthlyAmount.decAmount = monthlyAmount.decAmount + invoiceAmountArr[x][z][3]
                            monthlyInvoiceCount.decItemCount = monthlyInvoiceCount.decItemCount + 1
                        }
//                            }
                    }
                }
            }
        }

//        }

        monthlyInvoiceArr.add(monthlyAmount);
        monthlyInvoiceArr.add(monthlyInvoiceCount);

        return monthlyInvoiceArr;
    }


    def List<Map> getPrivateMonthlyInvoiceAmount(def budgetMasterId, def invoiceAmountArr) {

        List monthlyInvoiceArr = new ArrayList();
        Map monthlyAmount = ["janAmount": 0, "febAmount": 0, "marAmount": 0, "aprAmount": 0, "mayAmount": 0, "junAmount": 0,
                             "julAmount": 0, "augAmount": 0, "sepAmount": 0, "octAmount": 0, "novAmount": 0, "decAmount": 0];

        Map monthlyInvoiceCount = ["janItemCount": 0, "febItemCount": 0, "marItemCount": 0, "aprItemCount": 0, "mayItemCount": 0, "junItemCount": 0,
                                   "julItemCount": 0, "augItemCount": 0, "sepItemCount": 0, "octItemCount": 0, "novItemCount": 0, "decItemCount": 0];

        Map monthlyPrivateSpendingId = ["janPrivateSpendingId": 0, "febPrivateSpendingId": 0, "marPrivateSpendingId": 0,
                                        "aprPrivateSpendingId": 0, "mayPrivateSpendingId": 0, "junPrivateSpendingId": 0,
                                        "julPrivateSpendingId": 0, "augPrivateSpendingId": 0, "sepPrivateSpendingId": 0,
                                        "octPrivateSpendingId": 0, "novPrivateSpendingId": 0, "decPrivateSpendingId": 0];

        int u = 0;
        double summarytotalTempAmount = 0.0
//        def customerId = budgetCustomerIdArr[u];
//
//        if (bParseNeed) {
//            customerId = Integer.parseInt(budgetCustomerIdArr[u].toString());
//        }

        for (int x = 0; x < invoiceAmountArr.size(); x++) {

            if (invoiceAmountArr[x].size() > 0) {
                for (int z = 0; z < invoiceAmountArr[x].size(); z++) {
                    def tmpMasterId = invoiceAmountArr[x][z][0];

                    if (tmpMasterId == budgetMasterId) {

                        if ((invoiceAmountArr[x][z][1]) == 1) {
                            monthlyAmount.janAmount = monthlyAmount.janAmount + invoiceAmountArr[x][z][2]
                            monthlyInvoiceCount.janItemCount = monthlyInvoiceCount.janItemCount + 1
                            monthlyPrivateSpendingId.janPrivateSpendingId = invoiceAmountArr[x][z][3]
                        } else if ((invoiceAmountArr[x][z][1]) == 2) {
                            monthlyAmount.febAmount = monthlyAmount.febAmount + invoiceAmountArr[x][z][2]
                            monthlyInvoiceCount.febItemCount = monthlyInvoiceCount.febItemCount + 1
                            monthlyPrivateSpendingId.febPrivateSpendingId = invoiceAmountArr[x][z][3]
                        } else if ((invoiceAmountArr[x][z][1]) == 3) {
                            monthlyAmount.marAmount = monthlyAmount.marAmount + invoiceAmountArr[x][z][2]
                            monthlyInvoiceCount.marItemCount = monthlyInvoiceCount.marItemCount + 1
                            monthlyPrivateSpendingId.marPrivateSpendingId = invoiceAmountArr[x][z][3]
                        } else if ((invoiceAmountArr[x][z][1]) == 4) {
                            monthlyAmount.aprAmount = monthlyAmount.aprAmount + invoiceAmountArr[x][z][2]
                            monthlyInvoiceCount.aprItemCount = monthlyInvoiceCount.aprItemCount + 1
                            monthlyPrivateSpendingId.aprPrivateSpendingId = invoiceAmountArr[x][z][3]
                        } else if ((invoiceAmountArr[x][z][1]) == 5) {
                            monthlyAmount.mayAmount = monthlyAmount.mayAmount + invoiceAmountArr[x][z][2]
                            monthlyInvoiceCount.mayItemCount = monthlyInvoiceCount.mayItemCount + 1
                            monthlyPrivateSpendingId.mayPrivateSpendingId = invoiceAmountArr[x][z][3]
                        } else if ((invoiceAmountArr[x][z][1]) == 6) {
                            monthlyAmount.junAmount = monthlyAmount.junAmount + invoiceAmountArr[x][z][2]
                            monthlyInvoiceCount.junItemCount = monthlyInvoiceCount.junItemCount + 1
                            monthlyPrivateSpendingId.junPrivateSpendingId = invoiceAmountArr[x][z][3]
                        } else if ((invoiceAmountArr[x][z][1]) == 7) {
                            monthlyAmount.julAmount = monthlyAmount.julAmount + invoiceAmountArr[x][z][2]
                            monthlyInvoiceCount.julItemCount = monthlyInvoiceCount.julItemCount + 1
                            monthlyPrivateSpendingId.julPrivateSpendingId = invoiceAmountArr[x][z][3]
                        } else if ((invoiceAmountArr[x][z][1]) == 8) {
                            monthlyAmount.augAmount = monthlyAmount.augAmount + invoiceAmountArr[x][z][2]
                            monthlyInvoiceCount.augItemCount = monthlyInvoiceCount.augItemCount + 1
                            monthlyPrivateSpendingId.augPrivateSpendingId = invoiceAmountArr[x][z][3]
                        } else if ((invoiceAmountArr[x][z][1]) == 9) {
                            monthlyAmount.sepAmount = monthlyAmount.sepAmount + invoiceAmountArr[x][z][2]
                            monthlyInvoiceCount.sepItemCount = monthlyInvoiceCount.sepItemCount + 1
                            monthlyPrivateSpendingId.sepPrivateSpendingId = invoiceAmountArr[x][z][3]
                        } else if ((invoiceAmountArr[x][z][1]) == 10) {
                            monthlyAmount.octAmount = monthlyAmount.octAmount + invoiceAmountArr[x][z][2]
                            monthlyInvoiceCount.octItemCount = monthlyInvoiceCount.octItemCount + 1
                            monthlyPrivateSpendingId.octPrivateSpendingId = invoiceAmountArr[x][z][3]
                        } else if ((invoiceAmountArr[x][z][1]) == 11) {
                            monthlyAmount.novAmount = monthlyAmount.novAmount + invoiceAmountArr[x][z][2]
                            monthlyInvoiceCount.novItemCount = monthlyInvoiceCount.novItemCount + 1
                            monthlyPrivateSpendingId.novPrivateSpendingId = invoiceAmountArr[x][z][3]
                        } else if ((invoiceAmountArr[x][z][1]) == 12) {
                            monthlyAmount.decAmount = monthlyAmount.decAmount + invoiceAmountArr[x][z][2]
                            monthlyInvoiceCount.decItemCount = monthlyInvoiceCount.decItemCount + 1
                            monthlyPrivateSpendingId.decPrivateSpendingId = invoiceAmountArr[x][z][3]
                        }
                    }
                }
            }
        }

        monthlyInvoiceArr.add(monthlyAmount);
        monthlyInvoiceArr.add(monthlyInvoiceCount);
        monthlyInvoiceArr.add(monthlyPrivateSpendingId);

        return monthlyInvoiceArr;
    }

    def getCustomerGLAccountArray(def budgetCustomerInfo, def customerId) {

        def uniqueAcoount = "";
        ArrayList glAccountArr = new ArrayList()

        for (int i = 0; i < budgetCustomerInfo.size(); i++) {

            if (budgetCustomerInfo[i].size()) {
                def tmpCustomerId = budgetCustomerInfo[i][0][0]
                if (customerId == tmpCustomerId) {
                    uniqueAcoount = "";

                    for (int j = 0; j < budgetCustomerInfo[i].size(); j++) {
                        if (uniqueAcoount != budgetCustomerInfo[i][j][1]) {
                            uniqueAcoount = budgetCustomerInfo[i][j][1]
                            glAccountArr << budgetCustomerInfo[i][j][1]
                        }
                    }
                }
            }
        }

        return glAccountArr;
    }

    def getVendorGLAccountArray(def budgetVendorInfo, def vendorId, boolean bParseNeed) {

        def uniqueAcoount = "";
        ArrayList glAccountArr = new ArrayList()

        for (int i = 0; i < budgetVendorInfo.size(); i++) {

            if (budgetVendorInfo[i].size()) {
                def tmpVendorId = Integer.parseInt(budgetVendorInfo[i][0][0])

                //println("tmpVendorId "+tmpVendorId + "vendorId" + vendorId);
                if (vendorId == tmpVendorId) {
                    uniqueAcoount = "";

                    for (int j = 0; j < budgetVendorInfo[i].size(); j++) {
                        if (uniqueAcoount != budgetVendorInfo[i][j][1]) {
                            uniqueAcoount = budgetVendorInfo[i][j][1]
                            glAccountArr << budgetVendorInfo[i][j][1]
                        }
                    }
                }
            }
        }

        return glAccountArr;
    }


    def getReservationGLAccountArray(def budgetReservationInfo,def budgetNameId,boolean bParseNeed) {

        def uniqueAcoount = "";
        ArrayList glAccountArr = new ArrayList()

        for (int i = 0; i < budgetReservationInfo.size(); i++) {

            if (budgetReservationInfo[i].size()) {
                def tmpBudgetNameId = Integer.parseInt(budgetReservationInfo[i][0][0].toString())

                //println("tmpVendorId "+tmpVendorId + "vendorId" + vendorId);
                if (budgetNameId == tmpBudgetNameId) {
                    uniqueAcoount = "";

                    for (int j = 0; j < budgetReservationInfo[i].size(); j++) {
                        if (uniqueAcoount != budgetReservationInfo[i][j][1]) {
                            uniqueAcoount = budgetReservationInfo[i][j][1]
                            glAccountArr << budgetReservationInfo[i][j][1]
                        }
                    }
                }
            }
        }

        return glAccountArr;
    }




    def getBudgetCustomerIdArray(def budgetCustomerInfo, def customerId) {

        def uniqueAcoount = "";
        ArrayList budgetCustomerIdArr = new ArrayList()

        for (int i = 0; i < budgetCustomerInfo.size(); i++) {

            if (budgetCustomerInfo[i].size()) {
                def tmpCustomerId = budgetCustomerInfo[i][0][0]
                if (customerId == tmpCustomerId) {
                    uniqueAcoount = "";

                    for (int j = 0; j < budgetCustomerInfo[i].size(); j++) {
                        if (uniqueAcoount != budgetCustomerInfo[i][j][1]) {
                            uniqueAcoount = budgetCustomerInfo[i][j][1]
                            budgetCustomerIdArr << budgetCustomerInfo[i][j][0]
                        }
                    }
                }
            }
        }

        return budgetCustomerIdArr;
    }

    def getBudgetVendorIdArray(def budgetVendorArr, def vendorId, boolean bParseNeed) {

        def uniqueAcoount = "";
        ArrayList budgetVendorIdArr = new ArrayList()

        for (int i = 0; i < budgetVendorArr.size(); i++) {

            if (budgetVendorArr[i].size()) {
                def tmpVendorId = Integer.parseInt(budgetVendorArr[i][0][0])

                if (vendorId == tmpVendorId) {
                    uniqueAcoount = "";

                    for (int j = 0; j < budgetVendorArr[i].size(); j++) {
                        if (uniqueAcoount != budgetVendorArr[i][j][1]) {
                            uniqueAcoount = budgetVendorArr[i][j][1]
                            budgetVendorIdArr << budgetVendorArr[i][j][0]
                        }
                    }
                }
            }
        }

        return budgetVendorIdArr;
    }


    def getBudgetReservationArray(def budgetReservationArr,def BudgetNameId,boolean bParseNeed) {

        def uniqueAcoount = "";
        ArrayList budgetReservationIdArr = new ArrayList()

        for (int i = 0; i < budgetReservationArr.size(); i++) {

            if (budgetReservationArr[i].size()) {
                def tmpBudgetNameId = Integer.parseInt(budgetReservationArr[i][0][0].toString())

                if (BudgetNameId == tmpBudgetNameId) {
                    uniqueAcoount = "";

                    for (int j = 0; j < budgetReservationArr[i].size(); j++) {
                        if (uniqueAcoount != budgetReservationArr[i][j][1]) {
                            uniqueAcoount = budgetReservationArr[i][j][1]
                            budgetReservationIdArr << budgetReservationArr[i][j][0]
                        }
                    }
                }
            }
        }

        return budgetReservationIdArr;
    }

    def Integer getTickInterval(def monthlyBudgetAmount, def monthlyInvoiceAmount) {
        List<Integer> finalList = new ArrayList<Integer>();

        List<Integer> listBudgetAmount
        if(monthlyBudgetAmount){
            listBudgetAmount = new ArrayList<Integer>(monthlyBudgetAmount[0].values());
            finalList.addAll(listBudgetAmount);
        }

        List<Integer> listInvoiceAmount
        if(listInvoiceAmount){
            listInvoiceAmount = new ArrayList<Integer>(monthlyInvoiceAmount[0].values());
            finalList.addAll(listInvoiceAmount);
        }


        //
        Integer nMaxValue = getMax(finalList);
        Integer nTickInterval = 50;
        if (nMaxValue >= 500) {
            nTickInterval = nMaxValue / 10;
        } else if (nMaxValue <= 400 && nMaxValue > 10) {
            nTickInterval = nMaxValue / 5;
        } else if (nMaxValue <= 10 && nMaxValue > 0) {
            nTickInterval = 5;
        }

        return nTickInterval;
    }

    def Integer getTickIntervalForSummaryView(def arrayOne, def arrayTwo) {
        List<Integer> finalList = new ArrayList<Integer>();

        List<Integer> listOne = new ArrayList<Integer>(arrayOne.values());
        finalList.addAll(listOne);

        List<Integer> listTwo = new ArrayList<Integer>(arrayTwo.values());
        finalList.addAll(listTwo);

        //
        Integer nMaxValue = getMaxExceptional(finalList);
        Integer nTickInterval = 50;
        if (nMaxValue >= 500) {
            nTickInterval = nMaxValue / 10;
        } else if (nMaxValue <= 400 && nMaxValue > 10) {
            nTickInterval = nMaxValue / 5;
        } else if (nMaxValue < 10 && nMaxValue > 0) {
            nTickInterval = 5;
        }

        return nTickInterval;
    }

    def int getMax(ArrayList list) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) > max) {
                max = list.get(i);
            }
        }

        return max;
    }

    def int getMaxExceptional(ArrayList list) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < list.size(); i++) {
            int valTemp = list.get(i);
            if (valTemp < 0) {
                valTemp = valTemp * (-1);
            }

            if (max < 0) {
                max = max * (-1);
            }

            if (valTemp > max) {
                max = list.get(i);
            }
        }
        return max;
    }

    def List<Map> getMonthlyPrivateBudgetAmount(def budgetMasterId, def budgetAmountArr) {

        List monthlyBudgetArr = new ArrayList();
        Map monthlyAmount = ["janAmount": 0, "febAmount": 0, "marAmount": 0, "aprAmount": 0, "mayAmount": 0, "junAmount": 0,
                             "julAmount": 0, "augAmount": 0, "sepAmount": 0, "octAmount": 0, "novAmount": 0, "decAmount": 0];

        Map monthlyBudgetCount = ["janItemCount": 0, "febItemCount": 0, "marItemCount": 0, "aprItemCount": 0, "mayItemCount": 0, "junItemCount": 0,
                                  "julItemCount": 0, "augItemCount": 0, "sepItemCount": 0, "octItemCount": 0, "novItemCount": 0, "decItemCount": 0];

        Map monthlyBudgetId = ["janBudId": 0, "febBudId": 0, "marBudId": 0, "aprBudId": 0, "mayBudId": 0, "junBudId": 0,
                               "julBudId": 0, "augBudId": 0, "sepBudId": 0, "octBudId": 0, "novBudId": 0, "decBudId": 0];

        def budgetIdWiseBudgetCnt = [:]

        double summarytotalTempAmount = 0.0

        for (int x = 0; x < budgetAmountArr.size(); x++) {
            for (int z = 0; z < budgetAmountArr[x].size(); z++) {
                def tmpMasterId = budgetAmountArr[x][z][0];

                if (tmpMasterId == budgetMasterId) {

                    def budgetId = budgetAmountArr[x][z][3]
                    //Checking Booking Period
                    if (budgetAmountArr[x][z][1] == 1) {
                        monthlyAmount.janAmount = monthlyAmount.janAmount + budgetAmountArr[x][z][2]

                        def incrementedVal = budgetIdWiseBudgetCnt[budgetId]
                        if (incrementedVal != 1) {
                            monthlyBudgetCount.janItemCount = monthlyBudgetCount.janItemCount + 1
                        }

                        budgetIdWiseBudgetCnt[budgetId] = 1
                        monthlyBudgetId.janBudId = budgetId

                    } else if (budgetAmountArr[x][z][1] == 2) {
                        monthlyAmount.febAmount = monthlyAmount.febAmount + budgetAmountArr[x][z][2]


                        def incrementedVal = budgetIdWiseBudgetCnt[budgetId]
                        if (incrementedVal != 1) {
                            monthlyBudgetCount.febItemCount = monthlyBudgetCount.febItemCount + 1
                        }

                        budgetIdWiseBudgetCnt[budgetId] = 1
                        monthlyBudgetId.febBudId = budgetId

                    } else if (budgetAmountArr[x][z][1] == 3) {
                        monthlyAmount.marAmount = monthlyAmount.marAmount + budgetAmountArr[x][z][2]

                        def incrementedVal = budgetIdWiseBudgetCnt[budgetId]
                        if (incrementedVal != 1) {
                            monthlyBudgetCount.marItemCount = monthlyBudgetCount.marItemCount + 1
                        }
                        budgetIdWiseBudgetCnt[budgetId] = 1
                        monthlyBudgetId.marBudId = budgetId

                    } else if (budgetAmountArr[x][z][1] == 4) {
                        monthlyAmount.aprAmount = monthlyAmount.aprAmount + budgetAmountArr[x][z][2]
                        def incrementedVal = budgetIdWiseBudgetCnt[budgetId]
                        if (incrementedVal != 1) {
                            monthlyBudgetCount.aprItemCount = monthlyBudgetCount.aprItemCount + 1
                        }

                        budgetIdWiseBudgetCnt[budgetId] = 1
                        monthlyBudgetId.aprBudId = budgetId

                    } else if (budgetAmountArr[x][z][1] == 5) {
                        monthlyAmount.mayAmount = monthlyAmount.mayAmount + budgetAmountArr[x][z][2]

                        def incrementedVal = budgetIdWiseBudgetCnt[budgetId]
                        if (incrementedVal != 1) {
                            monthlyBudgetCount.mayItemCount = monthlyBudgetCount.mayItemCount + 1
                        }

                        budgetIdWiseBudgetCnt[budgetId] = 1
                        monthlyBudgetId.mayBudId = budgetId

                    } else if (budgetAmountArr[x][z][1] == 6) {
                        monthlyAmount.junAmount = monthlyAmount.junAmount + budgetAmountArr[x][z][2]

                        def incrementedVal = budgetIdWiseBudgetCnt[budgetId]
                        if (incrementedVal != 1) {
                            monthlyBudgetCount.junItemCount = monthlyBudgetCount.junItemCount + 1
                        }

                        budgetIdWiseBudgetCnt[budgetId] = 1
                        monthlyBudgetId.junBudId = budgetId

                    } else if (budgetAmountArr[x][z][1] == 7) {
                        monthlyAmount.julAmount = monthlyAmount.julAmount + budgetAmountArr[x][z][2]
                        def incrementedVal = budgetIdWiseBudgetCnt[budgetId]
                        if (incrementedVal != 1) {
                            monthlyBudgetCount.julItemCount = monthlyBudgetCount.julItemCount + 1
                        }

                        budgetIdWiseBudgetCnt[budgetId] = 1
                        monthlyBudgetId.julBudId = budgetId

                    } else if (budgetAmountArr[x][z][1] == 8) {
                        monthlyAmount.augAmount = monthlyAmount.augAmount + budgetAmountArr[x][z][2]
                        def incrementedVal = budgetIdWiseBudgetCnt[budgetId]
                        if (incrementedVal != 1) {
                            monthlyBudgetCount.augItemCount = monthlyBudgetCount.augItemCount + 1
                        }

                        budgetIdWiseBudgetCnt[budgetId] = 1
                        monthlyBudgetId.augBudId = budgetId
                    } else if (budgetAmountArr[x][z][1] == 9) {
                        monthlyAmount.sepAmount = monthlyAmount.sepAmount + budgetAmountArr[x][z][2]
                        def incrementedVal = budgetIdWiseBudgetCnt[budgetId]
                        if (incrementedVal != 1) {
                            monthlyBudgetCount.sepItemCount = monthlyBudgetCount.sepItemCount + 1
                        }

                        budgetIdWiseBudgetCnt[budgetId] = 1
                        monthlyBudgetId.sepBudId = budgetId

                    } else if (budgetAmountArr[x][z][1] == 10) {
                        monthlyAmount.octAmount = monthlyAmount.octAmount + budgetAmountArr[x][z][2]
                        def incrementedVal = budgetIdWiseBudgetCnt[budgetId]

                        if (incrementedVal != 1) {
                            monthlyBudgetCount.octItemCount = monthlyBudgetCount.octItemCount + 1
                        }

                        budgetIdWiseBudgetCnt[budgetId] = 1
                        monthlyBudgetId.octBudId = budgetId

                    } else if (budgetAmountArr[x][z][1] == 11) {
                        monthlyAmount.novAmount = monthlyAmount.novAmount + budgetAmountArr[x][z][2]
                        def incrementedVal = budgetIdWiseBudgetCnt[budgetId]
                        if (incrementedVal != 1) {
                            monthlyBudgetCount.novItemCount = monthlyBudgetCount.novItemCount + 1
                        }

                        budgetIdWiseBudgetCnt[budgetId] = 1
                        monthlyBudgetId.novBudId = budgetId

                    } else if (budgetAmountArr[x][z][1] == 12) {
                        monthlyAmount.decAmount = monthlyAmount.decAmount + budgetAmountArr[x][z][2]
                        def incrementedVal = budgetIdWiseBudgetCnt[budgetId]
                        if (incrementedVal != 1) {
                            monthlyBudgetCount.decItemCount = monthlyBudgetCount.decItemCount + 1
                        }

                        budgetIdWiseBudgetCnt[budgetId] = 1
                        monthlyBudgetId.decBudId = budgetId
                    }
                }
            }

        }

        monthlyBudgetArr.add(monthlyAmount);
        monthlyBudgetArr.add(monthlyBudgetCount);
        monthlyBudgetArr.add(monthlyBudgetId);

        return monthlyBudgetArr;
    }

    def getPositiveValue(def value){
        def result
        if(value < 0){
            result = value*(-1)
        }else{
            result= value
        }
        return result
    }

    def changeSignForPrivateBudget(def value,def budgetType){
        def result
        if(value < 0){
            result = value*(-1)
        }else{
            if(budgetType > 1){
                result = value*(-1)
            }else{
                result = value
            }
        }
        return result
    }


}
