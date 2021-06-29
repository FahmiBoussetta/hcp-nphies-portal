import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/ack-error-messages">
      <Translate contentKey="global.menu.entities.ackErrorMessages" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/claim-error-messages">
      <Translate contentKey="global.menu.entities.claimErrorMessages" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/cr-error-messages">
      <Translate contentKey="global.menu.entities.crErrorMessages" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/com-error-messages">
      <Translate contentKey="global.menu.entities.comErrorMessages" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/cov-eli-error-messages">
      <Translate contentKey="global.menu.entities.covEliErrorMessages" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/cov-eli-resp-error-messages">
      <Translate contentKey="global.menu.entities.covEliRespErrorMessages" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/ope-out-error-messages">
      <Translate contentKey="global.menu.entities.opeOutErrorMessages" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/pay-not-error-messages">
      <Translate contentKey="global.menu.entities.payNotErrorMessages" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/diagnosis-sequence">
      <Translate contentKey="global.menu.entities.diagnosisSequence" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/information-sequence">
      <Translate contentKey="global.menu.entities.informationSequence" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/adjudication-notes">
      <Translate contentKey="global.menu.entities.adjudicationNotes" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/adjudication-detail-notes">
      <Translate contentKey="global.menu.entities.adjudicationDetailNotes" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/adjudication-sub-detail-notes">
      <Translate contentKey="global.menu.entities.adjudicationSubDetailNotes" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/givens">
      <Translate contentKey="global.menu.entities.givens" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/list-communication-medium-enum">
      <Translate contentKey="global.menu.entities.listCommunicationMediumEnum" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/list-communication-reason-enum">
      <Translate contentKey="global.menu.entities.listCommunicationReasonEnum" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/list-eligibility-purpose-enum">
      <Translate contentKey="global.menu.entities.listEligibilityPurposeEnum" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/list-role-code-enum">
      <Translate contentKey="global.menu.entities.listRoleCodeEnum" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/list-specialty-enum">
      <Translate contentKey="global.menu.entities.listSpecialtyEnum" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/acknowledgement">
      <Translate contentKey="global.menu.entities.acknowledgement" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/address">
      <Translate contentKey="global.menu.entities.address" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/claim">
      <Translate contentKey="global.menu.entities.claim" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/related">
      <Translate contentKey="global.menu.entities.related" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/payee">
      <Translate contentKey="global.menu.entities.payee" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/care-team">
      <Translate contentKey="global.menu.entities.careTeam" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/diagnosis">
      <Translate contentKey="global.menu.entities.diagnosis" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/insurance">
      <Translate contentKey="global.menu.entities.insurance" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/accident">
      <Translate contentKey="global.menu.entities.accident" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/item">
      <Translate contentKey="global.menu.entities.item" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/detail-item">
      <Translate contentKey="global.menu.entities.detailItem" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/sub-detail-item">
      <Translate contentKey="global.menu.entities.subDetailItem" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/claim-response">
      <Translate contentKey="global.menu.entities.claimResponse" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/adjudication-item">
      <Translate contentKey="global.menu.entities.adjudicationItem" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/adjudication-detail-item">
      <Translate contentKey="global.menu.entities.adjudicationDetailItem" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/adjudication-sub-detail-item">
      <Translate contentKey="global.menu.entities.adjudicationSubDetailItem" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/adjudication">
      <Translate contentKey="global.menu.entities.adjudication" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/total">
      <Translate contentKey="global.menu.entities.total" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/communication">
      <Translate contentKey="global.menu.entities.communication" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/attachment">
      <Translate contentKey="global.menu.entities.attachment" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/payload">
      <Translate contentKey="global.menu.entities.payload" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/note">
      <Translate contentKey="global.menu.entities.note" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/communication-request">
      <Translate contentKey="global.menu.entities.communicationRequest" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/contact">
      <Translate contentKey="global.menu.entities.contact" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/coverage">
      <Translate contentKey="global.menu.entities.coverage" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/class-component">
      <Translate contentKey="global.menu.entities.classComponent" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/cost-to-beneficiary-component">
      <Translate contentKey="global.menu.entities.costToBeneficiaryComponent" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/exemption-component">
      <Translate contentKey="global.menu.entities.exemptionComponent" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/coverage-eligibility-request">
      <Translate contentKey="global.menu.entities.coverageEligibilityRequest" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/coverage-eligibility-response">
      <Translate contentKey="global.menu.entities.coverageEligibilityResponse" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/response-insurance">
      <Translate contentKey="global.menu.entities.responseInsurance" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/response-insurance-item">
      <Translate contentKey="global.menu.entities.responseInsuranceItem" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/insurance-benefit">
      <Translate contentKey="global.menu.entities.insuranceBenefit" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/encounter">
      <Translate contentKey="global.menu.entities.encounter" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/hospitalization">
      <Translate contentKey="global.menu.entities.hospitalization" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/human-name">
      <Translate contentKey="global.menu.entities.humanName" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/location">
      <Translate contentKey="global.menu.entities.location" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/operation-outcome">
      <Translate contentKey="global.menu.entities.operationOutcome" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/organization">
      <Translate contentKey="global.menu.entities.organization" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/patient">
      <Translate contentKey="global.menu.entities.patient" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/payment-notice">
      <Translate contentKey="global.menu.entities.paymentNotice" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/payment-reconciliation">
      <Translate contentKey="global.menu.entities.paymentReconciliation" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/reconciliation-detail-item">
      <Translate contentKey="global.menu.entities.reconciliationDetailItem" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/practitioner">
      <Translate contentKey="global.menu.entities.practitioner" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/practitioner-role">
      <Translate contentKey="global.menu.entities.practitionerRole" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/reference-identifier">
      <Translate contentKey="global.menu.entities.referenceIdentifier" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/supporting-info">
      <Translate contentKey="global.menu.entities.supportingInfo" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/quantity">
      <Translate contentKey="global.menu.entities.quantity" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/task">
      <Translate contentKey="global.menu.entities.task" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/task-input">
      <Translate contentKey="global.menu.entities.taskInput" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/task-response">
      <Translate contentKey="global.menu.entities.taskResponse" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/task-output">
      <Translate contentKey="global.menu.entities.taskOutput" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
