package bv

import org.springframework.web.context.request.RequestContextHolder

class FiscalYear {

    Date yearBegin
    Date yearEnd
    Integer status

    static constraints = {

    }


   static mapping = {
        //def myurl = RequestContextHolder?.getRequestAttributes()
        //def myurl = "SKBBB3BBBB"

       /* def ActiveDataSourceName = "shawpon"
        def ActiveDataSourceNameDD = new CoreParamsHelper().dataSourceName()
        Date now = new Date()


        println(now)
        println("RRR"+ActiveDataSourceName)
        println(ActiveDataSourceNameDD)


        if(ActiveDataSourceName=="shawpon"){
            datasource 'shawpon'
        }else if(ActiveDataSourceName=="bhowmic"){
            datasource 'bhowmic'
        }*/
    }

   /* def beforeInsert = {
        println("Shawpon11111111111111111111");
        FiscalYear.metaClass.static.mapping={}
        println(FiscalYear.metaClass.static.mapping={})
        println("Shawpon2222222222222222222");
       // Account.metaClass.static.getType = {return "Business"}

    }*/
    /*def beforeInsert = {
        //datasource 'shawpon'
        //datasource 'shawpon'

        def fileName = getClass().getProtectionDomain().getCodeSource().getLocation().getFile().replace(getClass().getSimpleName() + ".class", "").substring(1).replace("/classes","").replace("/target","")+"web-app/serverInfo/server.xml";


        println "partito il beforeinsert"
        println(fileName)



        FiscalYear.metaClass.static.mapping = {

            println("HASSAN")
            datasource "shawpon"
        }

    }*/




}
