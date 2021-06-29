import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import ackErrorMessages from 'app/entities/ack-error-messages/ack-error-messages.reducer';
// prettier-ignore
import claimErrorMessages from 'app/entities/claim-error-messages/claim-error-messages.reducer';
// prettier-ignore
import cRErrorMessages from 'app/entities/cr-error-messages/cr-error-messages.reducer';
// prettier-ignore
import comErrorMessages from 'app/entities/com-error-messages/com-error-messages.reducer';
// prettier-ignore
import covEliErrorMessages from 'app/entities/cov-eli-error-messages/cov-eli-error-messages.reducer';
// prettier-ignore
import covEliRespErrorMessages from 'app/entities/cov-eli-resp-error-messages/cov-eli-resp-error-messages.reducer';
// prettier-ignore
import opeOutErrorMessages from 'app/entities/ope-out-error-messages/ope-out-error-messages.reducer';
// prettier-ignore
import payNotErrorMessages from 'app/entities/pay-not-error-messages/pay-not-error-messages.reducer';
// prettier-ignore
import diagnosisSequence from 'app/entities/diagnosis-sequence/diagnosis-sequence.reducer';
// prettier-ignore
import informationSequence from 'app/entities/information-sequence/information-sequence.reducer';
// prettier-ignore
import adjudicationNotes from 'app/entities/adjudication-notes/adjudication-notes.reducer';
// prettier-ignore
import adjudicationDetailNotes from 'app/entities/adjudication-detail-notes/adjudication-detail-notes.reducer';
// prettier-ignore
import adjudicationSubDetailNotes from 'app/entities/adjudication-sub-detail-notes/adjudication-sub-detail-notes.reducer';
// prettier-ignore
import givens from 'app/entities/givens/givens.reducer';
// prettier-ignore
import listCommunicationMediumEnum from 'app/entities/list-communication-medium-enum/list-communication-medium-enum.reducer';
// prettier-ignore
import listCommunicationReasonEnum from 'app/entities/list-communication-reason-enum/list-communication-reason-enum.reducer';
// prettier-ignore
import listEligibilityPurposeEnum from 'app/entities/list-eligibility-purpose-enum/list-eligibility-purpose-enum.reducer';
// prettier-ignore
import listRoleCodeEnum from 'app/entities/list-role-code-enum/list-role-code-enum.reducer';
// prettier-ignore
import listSpecialtyEnum from 'app/entities/list-specialty-enum/list-specialty-enum.reducer';
// prettier-ignore
import acknowledgement from 'app/entities/acknowledgement/acknowledgement.reducer';
// prettier-ignore
import address from 'app/entities/address/address.reducer';
// prettier-ignore
import claim from 'app/entities/claim/claim.reducer';
// prettier-ignore
import related from 'app/entities/related/related.reducer';
// prettier-ignore
import payee from 'app/entities/payee/payee.reducer';
// prettier-ignore
import careTeam from 'app/entities/care-team/care-team.reducer';
// prettier-ignore
import diagnosis from 'app/entities/diagnosis/diagnosis.reducer';
// prettier-ignore
import insurance from 'app/entities/insurance/insurance.reducer';
// prettier-ignore
import accident from 'app/entities/accident/accident.reducer';
// prettier-ignore
import item from 'app/entities/item/item.reducer';
// prettier-ignore
import detailItem from 'app/entities/detail-item/detail-item.reducer';
// prettier-ignore
import subDetailItem from 'app/entities/sub-detail-item/sub-detail-item.reducer';
// prettier-ignore
import claimResponse from 'app/entities/claim-response/claim-response.reducer';
// prettier-ignore
import adjudicationItem from 'app/entities/adjudication-item/adjudication-item.reducer';
// prettier-ignore
import adjudicationDetailItem from 'app/entities/adjudication-detail-item/adjudication-detail-item.reducer';
// prettier-ignore
import adjudicationSubDetailItem from 'app/entities/adjudication-sub-detail-item/adjudication-sub-detail-item.reducer';
// prettier-ignore
import adjudication from 'app/entities/adjudication/adjudication.reducer';
// prettier-ignore
import total from 'app/entities/total/total.reducer';
// prettier-ignore
import communication from 'app/entities/communication/communication.reducer';
// prettier-ignore
import attachment from 'app/entities/attachment/attachment.reducer';
// prettier-ignore
import payload from 'app/entities/payload/payload.reducer';
// prettier-ignore
import note from 'app/entities/note/note.reducer';
// prettier-ignore
import communicationRequest from 'app/entities/communication-request/communication-request.reducer';
// prettier-ignore
import contact from 'app/entities/contact/contact.reducer';
// prettier-ignore
import coverage from 'app/entities/coverage/coverage.reducer';
// prettier-ignore
import classComponent from 'app/entities/class-component/class-component.reducer';
// prettier-ignore
import costToBeneficiaryComponent from 'app/entities/cost-to-beneficiary-component/cost-to-beneficiary-component.reducer';
// prettier-ignore
import exemptionComponent from 'app/entities/exemption-component/exemption-component.reducer';
// prettier-ignore
import coverageEligibilityRequest from 'app/entities/coverage-eligibility-request/coverage-eligibility-request.reducer';
// prettier-ignore
import coverageEligibilityResponse from 'app/entities/coverage-eligibility-response/coverage-eligibility-response.reducer';
// prettier-ignore
import responseInsurance from 'app/entities/response-insurance/response-insurance.reducer';
// prettier-ignore
import responseInsuranceItem from 'app/entities/response-insurance-item/response-insurance-item.reducer';
// prettier-ignore
import insuranceBenefit from 'app/entities/insurance-benefit/insurance-benefit.reducer';
// prettier-ignore
import encounter from 'app/entities/encounter/encounter.reducer';
// prettier-ignore
import hospitalization from 'app/entities/hospitalization/hospitalization.reducer';
// prettier-ignore
import humanName from 'app/entities/human-name/human-name.reducer';
// prettier-ignore
import location from 'app/entities/location/location.reducer';
// prettier-ignore
import operationOutcome from 'app/entities/operation-outcome/operation-outcome.reducer';
// prettier-ignore
import organization from 'app/entities/organization/organization.reducer';
// prettier-ignore
import patient from 'app/entities/patient/patient.reducer';
// prettier-ignore
import paymentNotice from 'app/entities/payment-notice/payment-notice.reducer';
// prettier-ignore
import paymentReconciliation from 'app/entities/payment-reconciliation/payment-reconciliation.reducer';
// prettier-ignore
import reconciliationDetailItem from 'app/entities/reconciliation-detail-item/reconciliation-detail-item.reducer';
// prettier-ignore
import practitioner from 'app/entities/practitioner/practitioner.reducer';
// prettier-ignore
import practitionerRole from 'app/entities/practitioner-role/practitioner-role.reducer';
// prettier-ignore
import referenceIdentifier from 'app/entities/reference-identifier/reference-identifier.reducer';
// prettier-ignore
import supportingInfo from 'app/entities/supporting-info/supporting-info.reducer';
// prettier-ignore
import quantity from 'app/entities/quantity/quantity.reducer';
// prettier-ignore
import task from 'app/entities/task/task.reducer';
// prettier-ignore
import taskInput from 'app/entities/task-input/task-input.reducer';
// prettier-ignore
import taskResponse from 'app/entities/task-response/task-response.reducer';
// prettier-ignore
import taskOutput from 'app/entities/task-output/task-output.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  ackErrorMessages,
  claimErrorMessages,
  cRErrorMessages,
  comErrorMessages,
  covEliErrorMessages,
  covEliRespErrorMessages,
  opeOutErrorMessages,
  payNotErrorMessages,
  diagnosisSequence,
  informationSequence,
  adjudicationNotes,
  adjudicationDetailNotes,
  adjudicationSubDetailNotes,
  givens,
  listCommunicationMediumEnum,
  listCommunicationReasonEnum,
  listEligibilityPurposeEnum,
  listRoleCodeEnum,
  listSpecialtyEnum,
  acknowledgement,
  address,
  claim,
  related,
  payee,
  careTeam,
  diagnosis,
  insurance,
  accident,
  item,
  detailItem,
  subDetailItem,
  claimResponse,
  adjudicationItem,
  adjudicationDetailItem,
  adjudicationSubDetailItem,
  adjudication,
  total,
  communication,
  attachment,
  payload,
  note,
  communicationRequest,
  contact,
  coverage,
  classComponent,
  costToBeneficiaryComponent,
  exemptionComponent,
  coverageEligibilityRequest,
  coverageEligibilityResponse,
  responseInsurance,
  responseInsuranceItem,
  insuranceBenefit,
  encounter,
  hospitalization,
  humanName,
  location,
  operationOutcome,
  organization,
  patient,
  paymentNotice,
  paymentReconciliation,
  reconciliationDetailItem,
  practitioner,
  practitionerRole,
  referenceIdentifier,
  supportingInfo,
  quantity,
  task,
  taskInput,
  taskResponse,
  taskOutput,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
