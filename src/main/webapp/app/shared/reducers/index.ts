import { combineReducers } from 'redux';
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
import ackErrorMessages, {
  AckErrorMessagesState
} from 'app/entities/ack-error-messages/ack-error-messages.reducer';
// prettier-ignore
import claimErrorMessages, {
  ClaimErrorMessagesState
} from 'app/entities/claim-error-messages/claim-error-messages.reducer';
// prettier-ignore
import cRErrorMessages, {
  CRErrorMessagesState
} from 'app/entities/cr-error-messages/cr-error-messages.reducer';
// prettier-ignore
import comErrorMessages, {
  ComErrorMessagesState
} from 'app/entities/com-error-messages/com-error-messages.reducer';
// prettier-ignore
import covEliErrorMessages, {
  CovEliErrorMessagesState
} from 'app/entities/cov-eli-error-messages/cov-eli-error-messages.reducer';
// prettier-ignore
import covEliRespErrorMessages, {
  CovEliRespErrorMessagesState
} from 'app/entities/cov-eli-resp-error-messages/cov-eli-resp-error-messages.reducer';
// prettier-ignore
import opeOutErrorMessages, {
  OpeOutErrorMessagesState
} from 'app/entities/ope-out-error-messages/ope-out-error-messages.reducer';
// prettier-ignore
import payNotErrorMessages, {
  PayNotErrorMessagesState
} from 'app/entities/pay-not-error-messages/pay-not-error-messages.reducer';
// prettier-ignore
import diagnosisSequence, {
  DiagnosisSequenceState
} from 'app/entities/diagnosis-sequence/diagnosis-sequence.reducer';
// prettier-ignore
import informationSequence, {
  InformationSequenceState
} from 'app/entities/information-sequence/information-sequence.reducer';
// prettier-ignore
import adjudicationNotes, {
  AdjudicationNotesState
} from 'app/entities/adjudication-notes/adjudication-notes.reducer';
// prettier-ignore
import adjudicationDetailNotes, {
  AdjudicationDetailNotesState
} from 'app/entities/adjudication-detail-notes/adjudication-detail-notes.reducer';
// prettier-ignore
import adjudicationSubDetailNotes, {
  AdjudicationSubDetailNotesState
} from 'app/entities/adjudication-sub-detail-notes/adjudication-sub-detail-notes.reducer';
// prettier-ignore
import givens, {
  GivensState
} from 'app/entities/givens/givens.reducer';
// prettier-ignore
import listCommunicationMediumEnum, {
  ListCommunicationMediumEnumState
} from 'app/entities/list-communication-medium-enum/list-communication-medium-enum.reducer';
// prettier-ignore
import listCommunicationReasonEnum, {
  ListCommunicationReasonEnumState
} from 'app/entities/list-communication-reason-enum/list-communication-reason-enum.reducer';
// prettier-ignore
import listEligibilityPurposeEnum, {
  ListEligibilityPurposeEnumState
} from 'app/entities/list-eligibility-purpose-enum/list-eligibility-purpose-enum.reducer';
// prettier-ignore
import listRoleCodeEnum, {
  ListRoleCodeEnumState
} from 'app/entities/list-role-code-enum/list-role-code-enum.reducer';
// prettier-ignore
import listSpecialtyEnum, {
  ListSpecialtyEnumState
} from 'app/entities/list-specialty-enum/list-specialty-enum.reducer';
// prettier-ignore
import acknowledgement, {
  AcknowledgementState
} from 'app/entities/acknowledgement/acknowledgement.reducer';
// prettier-ignore
import address, {
  AddressState
} from 'app/entities/address/address.reducer';
// prettier-ignore
import claim, {
  ClaimState
} from 'app/entities/claim/claim.reducer';
// prettier-ignore
import related, {
  RelatedState
} from 'app/entities/related/related.reducer';
// prettier-ignore
import payee, {
  PayeeState
} from 'app/entities/payee/payee.reducer';
// prettier-ignore
import careTeam, {
  CareTeamState
} from 'app/entities/care-team/care-team.reducer';
// prettier-ignore
import diagnosis, {
  DiagnosisState
} from 'app/entities/diagnosis/diagnosis.reducer';
// prettier-ignore
import insurance, {
  InsuranceState
} from 'app/entities/insurance/insurance.reducer';
// prettier-ignore
import accident, {
  AccidentState
} from 'app/entities/accident/accident.reducer';
// prettier-ignore
import item, {
  ItemState
} from 'app/entities/item/item.reducer';
// prettier-ignore
import detailItem, {
  DetailItemState
} from 'app/entities/detail-item/detail-item.reducer';
// prettier-ignore
import subDetailItem, {
  SubDetailItemState
} from 'app/entities/sub-detail-item/sub-detail-item.reducer';
// prettier-ignore
import claimResponse, {
  ClaimResponseState
} from 'app/entities/claim-response/claim-response.reducer';
// prettier-ignore
import adjudicationItem, {
  AdjudicationItemState
} from 'app/entities/adjudication-item/adjudication-item.reducer';
// prettier-ignore
import adjudicationDetailItem, {
  AdjudicationDetailItemState
} from 'app/entities/adjudication-detail-item/adjudication-detail-item.reducer';
// prettier-ignore
import adjudicationSubDetailItem, {
  AdjudicationSubDetailItemState
} from 'app/entities/adjudication-sub-detail-item/adjudication-sub-detail-item.reducer';
// prettier-ignore
import adjudication, {
  AdjudicationState
} from 'app/entities/adjudication/adjudication.reducer';
// prettier-ignore
import total, {
  TotalState
} from 'app/entities/total/total.reducer';
// prettier-ignore
import communication, {
  CommunicationState
} from 'app/entities/communication/communication.reducer';
// prettier-ignore
import attachment, {
  AttachmentState
} from 'app/entities/attachment/attachment.reducer';
// prettier-ignore
import payload, {
  PayloadState
} from 'app/entities/payload/payload.reducer';
// prettier-ignore
import note, {
  NoteState
} from 'app/entities/note/note.reducer';
// prettier-ignore
import communicationRequest, {
  CommunicationRequestState
} from 'app/entities/communication-request/communication-request.reducer';
// prettier-ignore
import contact, {
  ContactState
} from 'app/entities/contact/contact.reducer';
// prettier-ignore
import coverage, {
  CoverageState
} from 'app/entities/coverage/coverage.reducer';
// prettier-ignore
import classComponent, {
  ClassComponentState
} from 'app/entities/class-component/class-component.reducer';
// prettier-ignore
import costToBeneficiaryComponent, {
  CostToBeneficiaryComponentState
} from 'app/entities/cost-to-beneficiary-component/cost-to-beneficiary-component.reducer';
// prettier-ignore
import exemptionComponent, {
  ExemptionComponentState
} from 'app/entities/exemption-component/exemption-component.reducer';
// prettier-ignore
import coverageEligibilityRequest, {
  CoverageEligibilityRequestState
} from 'app/entities/coverage-eligibility-request/coverage-eligibility-request.reducer';
// prettier-ignore
import coverageEligibilityResponse, {
  CoverageEligibilityResponseState
} from 'app/entities/coverage-eligibility-response/coverage-eligibility-response.reducer';
// prettier-ignore
import responseInsurance, {
  ResponseInsuranceState
} from 'app/entities/response-insurance/response-insurance.reducer';
// prettier-ignore
import responseInsuranceItem, {
  ResponseInsuranceItemState
} from 'app/entities/response-insurance-item/response-insurance-item.reducer';
// prettier-ignore
import insuranceBenefit, {
  InsuranceBenefitState
} from 'app/entities/insurance-benefit/insurance-benefit.reducer';
// prettier-ignore
import encounter, {
  EncounterState
} from 'app/entities/encounter/encounter.reducer';
// prettier-ignore
import hospitalization, {
  HospitalizationState
} from 'app/entities/hospitalization/hospitalization.reducer';
// prettier-ignore
import humanName, {
  HumanNameState
} from 'app/entities/human-name/human-name.reducer';
// prettier-ignore
import location, {
  LocationState
} from 'app/entities/location/location.reducer';
// prettier-ignore
import operationOutcome, {
  OperationOutcomeState
} from 'app/entities/operation-outcome/operation-outcome.reducer';
// prettier-ignore
import organization, {
  OrganizationState
} from 'app/entities/organization/organization.reducer';
// prettier-ignore
import patient, {
  PatientState
} from 'app/entities/patient/patient.reducer';
// prettier-ignore
import paymentNotice, {
  PaymentNoticeState
} from 'app/entities/payment-notice/payment-notice.reducer';
// prettier-ignore
import paymentReconciliation, {
  PaymentReconciliationState
} from 'app/entities/payment-reconciliation/payment-reconciliation.reducer';
// prettier-ignore
import reconciliationDetailItem, {
  ReconciliationDetailItemState
} from 'app/entities/reconciliation-detail-item/reconciliation-detail-item.reducer';
// prettier-ignore
import practitioner, {
  PractitionerState
} from 'app/entities/practitioner/practitioner.reducer';
// prettier-ignore
import practitionerRole, {
  PractitionerRoleState
} from 'app/entities/practitioner-role/practitioner-role.reducer';
// prettier-ignore
import referenceIdentifier, {
  ReferenceIdentifierState
} from 'app/entities/reference-identifier/reference-identifier.reducer';
// prettier-ignore
import supportingInfo, {
  SupportingInfoState
} from 'app/entities/supporting-info/supporting-info.reducer';
// prettier-ignore
import quantity, {
  QuantityState
} from 'app/entities/quantity/quantity.reducer';
// prettier-ignore
import task, {
  TaskState
} from 'app/entities/task/task.reducer';
// prettier-ignore
import taskInput, {
  TaskInputState
} from 'app/entities/task-input/task-input.reducer';
// prettier-ignore
import taskResponse, {
  TaskResponseState
} from 'app/entities/task-response/task-response.reducer';
// prettier-ignore
import taskOutput, {
  TaskOutputState
} from 'app/entities/task-output/task-output.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly ackErrorMessages: AckErrorMessagesState;
  readonly claimErrorMessages: ClaimErrorMessagesState;
  readonly cRErrorMessages: CRErrorMessagesState;
  readonly comErrorMessages: ComErrorMessagesState;
  readonly covEliErrorMessages: CovEliErrorMessagesState;
  readonly covEliRespErrorMessages: CovEliRespErrorMessagesState;
  readonly opeOutErrorMessages: OpeOutErrorMessagesState;
  readonly payNotErrorMessages: PayNotErrorMessagesState;
  readonly diagnosisSequence: DiagnosisSequenceState;
  readonly informationSequence: InformationSequenceState;
  readonly adjudicationNotes: AdjudicationNotesState;
  readonly adjudicationDetailNotes: AdjudicationDetailNotesState;
  readonly adjudicationSubDetailNotes: AdjudicationSubDetailNotesState;
  readonly givens: GivensState;
  readonly listCommunicationMediumEnum: ListCommunicationMediumEnumState;
  readonly listCommunicationReasonEnum: ListCommunicationReasonEnumState;
  readonly listEligibilityPurposeEnum: ListEligibilityPurposeEnumState;
  readonly listRoleCodeEnum: ListRoleCodeEnumState;
  readonly listSpecialtyEnum: ListSpecialtyEnumState;
  readonly acknowledgement: AcknowledgementState;
  readonly address: AddressState;
  readonly claim: ClaimState;
  readonly related: RelatedState;
  readonly payee: PayeeState;
  readonly careTeam: CareTeamState;
  readonly diagnosis: DiagnosisState;
  readonly insurance: InsuranceState;
  readonly accident: AccidentState;
  readonly item: ItemState;
  readonly detailItem: DetailItemState;
  readonly subDetailItem: SubDetailItemState;
  readonly claimResponse: ClaimResponseState;
  readonly adjudicationItem: AdjudicationItemState;
  readonly adjudicationDetailItem: AdjudicationDetailItemState;
  readonly adjudicationSubDetailItem: AdjudicationSubDetailItemState;
  readonly adjudication: AdjudicationState;
  readonly total: TotalState;
  readonly communication: CommunicationState;
  readonly attachment: AttachmentState;
  readonly payload: PayloadState;
  readonly note: NoteState;
  readonly communicationRequest: CommunicationRequestState;
  readonly contact: ContactState;
  readonly coverage: CoverageState;
  readonly classComponent: ClassComponentState;
  readonly costToBeneficiaryComponent: CostToBeneficiaryComponentState;
  readonly exemptionComponent: ExemptionComponentState;
  readonly coverageEligibilityRequest: CoverageEligibilityRequestState;
  readonly coverageEligibilityResponse: CoverageEligibilityResponseState;
  readonly responseInsurance: ResponseInsuranceState;
  readonly responseInsuranceItem: ResponseInsuranceItemState;
  readonly insuranceBenefit: InsuranceBenefitState;
  readonly encounter: EncounterState;
  readonly hospitalization: HospitalizationState;
  readonly humanName: HumanNameState;
  readonly location: LocationState;
  readonly operationOutcome: OperationOutcomeState;
  readonly organization: OrganizationState;
  readonly patient: PatientState;
  readonly paymentNotice: PaymentNoticeState;
  readonly paymentReconciliation: PaymentReconciliationState;
  readonly reconciliationDetailItem: ReconciliationDetailItemState;
  readonly practitioner: PractitionerState;
  readonly practitionerRole: PractitionerRoleState;
  readonly referenceIdentifier: ReferenceIdentifierState;
  readonly supportingInfo: SupportingInfoState;
  readonly quantity: QuantityState;
  readonly task: TaskState;
  readonly taskInput: TaskInputState;
  readonly taskResponse: TaskResponseState;
  readonly taskOutput: TaskOutputState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
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
});

export default rootReducer;
