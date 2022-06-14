package com.proglint.businessrulesdemo.controller

import com.proglint.businessrulesdemo.model.Customer
import com.proglint.businessrulesdemo.model.PremiumResult
import org.kie.api.runtime.KieContainer
import org.kie.api.runtime.KieSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/premium/")
class PremiumController {
    @Autowired
    KieContainer kieContainer

    @PostMapping("/calculate")
    Map<String, Object> calculatePremium(@RequestBody Customer customer) {
        PremiumResult premiumResult = new PremiumResult()
        KieSession kieSession = kieContainer.newKieSession()
        kieSession.setGlobal("premiumResult", premiumResult)
        kieSession.insert(customer)
        kieSession.fireAllRules()
        kieSession.dispose()
        return [result: premiumResult]
    }
}
