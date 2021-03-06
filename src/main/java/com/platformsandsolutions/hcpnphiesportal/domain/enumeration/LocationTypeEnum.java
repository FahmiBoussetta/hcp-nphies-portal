package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The LocationTypeEnum enumeration.
 */
public enum LocationTypeEnum {
    DedicatedServiceDeliveryLocationRoleType,
    DedicatedClinicalLocationRoleType,
    DX("Diagnostics or therapeutics unit"),
    CVDX("Cardiovascular diagnostics or therapeutics unit"),
    CATH("Cardiac catheterization lab"),
    ECHO("Echocardiography lab"),
    GIDX("Gastroenterology diagnostics or therapeutics lab"),
    ENDOS("Endoscopy lab"),
    RADDX("Radiology diagnostics or therapeutics unit"),
    RADO("Radiation oncology unit"),
    RNEU("Neuroradiology unit"),
    HOSP("Hospital"),
    CHR("Chronic Care Facility"),
    GACH("Hospitals; General Acute Care Hospital"),
    MHSP("Military Hospital"),
    PSYCHF("Psychatric Care Facility"),
    RH("Rehabilitation hospital"),
    RHAT("addiction treatment center"),
    RHII("intellectual impairment center"),
    RHMAD("parents with adjustment difficulties center"),
    RHPI("physical impairment center"),
    RHPIH("physical impairment - hearing center"),
    RHPIMS("physical impairment - motor skills center"),
    RHPIVS("physical impairment - visual skills center"),
    RHYAD("youths with adjustment difficulties center"),
    HU("Hospital unit"),
    BMTU("Bone marrow transplant unit"),
    CCU("Coronary care unit"),
    CHEST("Chest unit"),
    EPIL("Epilepsy unit"),
    ER("Emergency room"),
    ETU("Emergency trauma unit"),
    HD("Hemodialysis unit"),
    HLAB("hospital laboratory"),
    INLAB("inpatient laboratory"),
    OUTLAB("outpatient laboratory"),
    HRAD("radiology unit"),
    HUSCS("specimen collection site"),
    ICU("Intensive care unit"),
    PEDICU("Pediatric intensive care unit"),
    PEDNICU("Pediatric neonatal intensive care unit"),
    INPHARM("inpatient pharmacy"),
    MBL("medical laboratory"),
    NCCS("Neurology critical care and stroke unit"),
    NS("Neurosurgery unit"),
    OUTPHARM("outpatient pharmacy"),
    PEDU("Pediatric unit"),
    PHU("Psychiatric hospital unit"),
    RHU("Rehabilitation hospital unit"),
    SLEEP("Sleep disorders unit"),
    NCCF("Nursing or custodial care facility"),
    SNF("Skilled nursing facility"),
    OF("Outpatient facility"),
    ALL("Allergy clinic"),
    AMPUT("Amputee clinic"),
    BMTC("Bone marrow transplant clinic"),
    BREAST("Breast clinic"),
    CANC("Child and adolescent neurology clinic"),
    CAPC("Child and adolescent psychiatry clinic"),
    CARD("Ambulatory Health Care Facilities; Clinic/Center; Rehabilitation: Cardiac Facilities"),
    PEDCARD("Pediatric cardiology clinic"),
    COAG("Coagulation clinic"),
    CRS("Colon and rectal surgery clinic"),
    DERM("Dermatology clinic"),
    ENDO("Endocrinology clinic"),
    PEDE("Pediatric endocrinology clinic"),
    ENT("Otorhinolaryngology clinic"),
    FMC("Family medicine clinic"),
    GI("Gastroenterology clinic"),
    PEDGI("Pediatric gastroenterology clinic"),
    GIM("General internal medicine clinic"),
    GYN("Gynecology clinic"),
    HEM("Hematology clinic"),
    PEDHEM("Pediatric hematology clinic"),
    HTN("Hypertension clinic"),
    IEC("Impairment evaluation center"),
    INFD("Infectious disease clinic"),
    PEDID("Pediatric infectious disease clinic"),
    INV("Infertility clinic"),
    LYMPH("Lympedema clinic"),
    MGEN("Medical genetics clinic"),
    NEPH("Nephrology clinic"),
    PEDNEPH("Pediatric nephrology clinic"),
    NEUR("Neurology clinic"),
    OB("Obstetrics clinic"),
    OMS("Oral and maxillofacial surgery clinic"),
    ONCL("Medical oncology clinic"),
    PEDHO("Pediatric oncology clinic"),
    OPH("Opthalmology clinic"),
    OPTC("optometry clinic"),
    ORTHO("Orthopedics clinic"),
    HAND("Hand clinic"),
    PAINCL("Pain clinic"),
    PC("Primary care clinic"),
    PEDC("Pediatrics clinic"),
    PEDRHEUM("Pediatric rheumatology clinic"),
    POD("Podiatry clinic"),
    PREV("Preventive medicine clinic"),
    PROCTO("Proctology clinic"),
    PROFF("Provider&#39;s Office"),
    PROS("Prosthodontics clinic"),
    PSI("Psychology clinic"),
    PSY("Psychiatry clinic"),
    RHEUM("Rheumatology clinic"),
    SPMED("Sports medicine clinic"),
    SU("Surgery clinic"),
    PLS("Plastic surgery clinic"),
    URO("Urology clinic"),
    TR("Transplant clinic"),
    TRAVEL("Travel and geographic medicine clinic"),
    WND("Wound clinic"),
    RTF("Residential treatment facility"),
    PRC("Pain rehabilitation center"),
    SURF("Substance use rehabilitation facility"),
    DedicatedNonClinicalLocationRoleType,
    DADDR("Delivery Address"),
    MOBL("Mobile Unit"),
    AMB("Ambulance"),
    PHARM("Pharmacy"),
    IncidentalServiceDeliveryLocationRoleType,
    ACC("accident site"),
    COMM("Community Location"),
    CSC("community service center"),
    PTRES("Patient&#39;s Residence"),
    SCHOOL("school"),
    UPC("underage protection center"),
    WORK("work site");

    private final String value;

    LocationTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
