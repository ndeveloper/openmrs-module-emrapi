/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.emrapi.web.controller;

import org.openmrs.module.emrapi.encounter.ActiveEncounterParameters;
import org.openmrs.module.emrapi.encounter.EmrEncounterService;
import org.openmrs.module.emrapi.encounter.EncounterSearchParameters;
import org.openmrs.module.emrapi.encounter.domain.EncounterTransaction;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/rest/emrapi/encounter")
public class EmrEncounterController extends BaseRestController {

    @Autowired
    private EmrEncounterService emrEncounterService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public EncounterTransaction update(@RequestBody EncounterTransaction encounterTransaction) {
        return emrEncounterService.save(encounterTransaction);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/active")
    @ResponseBody
    public EncounterTransaction getActiveEncounter(ActiveEncounterParameters activeEncounterParameters) {
        return emrEncounterService.getActiveEncounter(activeEncounterParameters);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{uuid}")
    @ResponseBody
    public EncounterTransaction get(@PathVariable("uuid") String uuid, Boolean includeAll) {
        return emrEncounterService.getEncounterTransaction(uuid, includeAll);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<EncounterTransaction> find(@RequestParam List<String> visitUuids, @RequestParam String patientUuid,
                                           @RequestParam List<String> vistiTypeUuids, @RequestParam Date encounterDateTimeFrom,
                                           @RequestParam Date encounterDateTimeTo, @RequestParam List<String> providerUuids,
                                           @RequestParam List<String> encounterTypeUuids, @RequestParam String locationUuid,
                                           @RequestParam Boolean includeAll) {
        EncounterSearchParameters encounterSearchParameters = new EncounterSearchParameters(visitUuids, patientUuid, vistiTypeUuids, encounterDateTimeFrom,
                encounterDateTimeTo, providerUuids, encounterTypeUuids, locationUuid, includeAll);
        return emrEncounterService.find(encounterSearchParameters);
    }
}
