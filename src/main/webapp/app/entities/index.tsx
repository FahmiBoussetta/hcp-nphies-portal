import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AckErrorMessages from './ack-error-messages';
import ClaimErrorMessages from './claim-error-messages';
import CRErrorMessages from './cr-error-messages';
import ComErrorMessages from './com-error-messages';
import CovEliErrorMessages from './cov-eli-error-messages';
import CovEliRespErrorMessages from './cov-eli-resp-error-messages';
import OpeOutErrorMessages from './ope-out-error-messages';
import PayNotErrorMessages from './pay-not-error-messages';
import DiagnosisSequence from './diagnosis-sequence';
import InformationSequence from './information-sequence';
import AdjudicationNotes from './adjudication-notes';
import AdjudicationDetailNotes from './adjudication-detail-notes';
import AdjudicationSubDetailNotes from './adjudication-sub-detail-notes';
import Givens from './givens';
import ListCommunicationMediumEnum from './list-communication-medium-enum';
import ListCommunicationReasonEnum from './list-communication-reason-enum';
import ListEligibilityPurposeEnum from './list-eligibility-purpose-enum';
import ListRoleCodeEnum from './list-role-code-enum';
import ListSpecialtyEnum from './list-specialty-enum';
import Acknowledgement from './acknowledgement';
import Address from './address';
import Claim from './claim';
import Related from './related';
import Payee from './payee';
import CareTeam from './care-team';
import Diagnosis from './diagnosis';
import Insurance from './insurance';
import Accident from './accident';
import Item from './item';
import DetailItem from './detail-item';
import SubDetailItem from './sub-detail-item';
import ClaimResponse from './claim-response';
import AdjudicationItem from './adjudication-item';
import AdjudicationDetailItem from './adjudication-detail-item';
import AdjudicationSubDetailItem from './adjudication-sub-detail-item';
import Adjudication from './adjudication';
import Total from './total';
import Communication from './communication';
import Attachment from './attachment';
import Payload from './payload';
import Note from './note';
import CommunicationRequest from './communication-request';
import Contact from './contact';
import Coverage from './coverage';
import ClassComponent from './class-component';
import CostToBeneficiaryComponent from './cost-to-beneficiary-component';
import ExemptionComponent from './exemption-component';
import CoverageEligibilityRequest from './coverage-eligibility-request';
import CoverageEligibilityResponse from './coverage-eligibility-response';
import ResponseInsurance from './response-insurance';
import ResponseInsuranceItem from './response-insurance-item';
import InsuranceBenefit from './insurance-benefit';
import Encounter from './encounter';
import Hospitalization from './hospitalization';
import HumanName from './human-name';
import Location from './location';
import OperationOutcome from './operation-outcome';
import Organization from './organization';
import Patient from './patient';
import PaymentNotice from './payment-notice';
import PaymentReconciliation from './payment-reconciliation';
import ReconciliationDetailItem from './reconciliation-detail-item';
import Practitioner from './practitioner';
import PractitionerRole from './practitioner-role';
import ReferenceIdentifier from './reference-identifier';
import SupportingInfo from './supporting-info';
import Quantity from './quantity';
import Task from './task';
import TaskInput from './task-input';
import TaskResponse from './task-response';
import TaskOutput from './task-output';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}ack-error-messages`} component={AckErrorMessages} />
      <ErrorBoundaryRoute path={`${match.url}claim-error-messages`} component={ClaimErrorMessages} />
      <ErrorBoundaryRoute path={`${match.url}cr-error-messages`} component={CRErrorMessages} />
      <ErrorBoundaryRoute path={`${match.url}com-error-messages`} component={ComErrorMessages} />
      <ErrorBoundaryRoute path={`${match.url}cov-eli-error-messages`} component={CovEliErrorMessages} />
      <ErrorBoundaryRoute path={`${match.url}cov-eli-resp-error-messages`} component={CovEliRespErrorMessages} />
      <ErrorBoundaryRoute path={`${match.url}ope-out-error-messages`} component={OpeOutErrorMessages} />
      <ErrorBoundaryRoute path={`${match.url}pay-not-error-messages`} component={PayNotErrorMessages} />
      <ErrorBoundaryRoute path={`${match.url}diagnosis-sequence`} component={DiagnosisSequence} />
      <ErrorBoundaryRoute path={`${match.url}information-sequence`} component={InformationSequence} />
      <ErrorBoundaryRoute path={`${match.url}adjudication-notes`} component={AdjudicationNotes} />
      <ErrorBoundaryRoute path={`${match.url}adjudication-detail-notes`} component={AdjudicationDetailNotes} />
      <ErrorBoundaryRoute path={`${match.url}adjudication-sub-detail-notes`} component={AdjudicationSubDetailNotes} />
      <ErrorBoundaryRoute path={`${match.url}givens`} component={Givens} />
      <ErrorBoundaryRoute path={`${match.url}list-communication-medium-enum`} component={ListCommunicationMediumEnum} />
      <ErrorBoundaryRoute path={`${match.url}list-communication-reason-enum`} component={ListCommunicationReasonEnum} />
      <ErrorBoundaryRoute path={`${match.url}list-eligibility-purpose-enum`} component={ListEligibilityPurposeEnum} />
      <ErrorBoundaryRoute path={`${match.url}list-role-code-enum`} component={ListRoleCodeEnum} />
      <ErrorBoundaryRoute path={`${match.url}list-specialty-enum`} component={ListSpecialtyEnum} />
      <ErrorBoundaryRoute path={`${match.url}acknowledgement`} component={Acknowledgement} />
      <ErrorBoundaryRoute path={`${match.url}address`} component={Address} />
      <ErrorBoundaryRoute path={`${match.url}claim`} component={Claim} />
      <ErrorBoundaryRoute path={`${match.url}related`} component={Related} />
      <ErrorBoundaryRoute path={`${match.url}payee`} component={Payee} />
      <ErrorBoundaryRoute path={`${match.url}care-team`} component={CareTeam} />
      <ErrorBoundaryRoute path={`${match.url}diagnosis`} component={Diagnosis} />
      <ErrorBoundaryRoute path={`${match.url}insurance`} component={Insurance} />
      <ErrorBoundaryRoute path={`${match.url}accident`} component={Accident} />
      <ErrorBoundaryRoute path={`${match.url}item`} component={Item} />
      <ErrorBoundaryRoute path={`${match.url}detail-item`} component={DetailItem} />
      <ErrorBoundaryRoute path={`${match.url}sub-detail-item`} component={SubDetailItem} />
      <ErrorBoundaryRoute path={`${match.url}claim-response`} component={ClaimResponse} />
      <ErrorBoundaryRoute path={`${match.url}adjudication-item`} component={AdjudicationItem} />
      <ErrorBoundaryRoute path={`${match.url}adjudication-detail-item`} component={AdjudicationDetailItem} />
      <ErrorBoundaryRoute path={`${match.url}adjudication-sub-detail-item`} component={AdjudicationSubDetailItem} />
      <ErrorBoundaryRoute path={`${match.url}adjudication`} component={Adjudication} />
      <ErrorBoundaryRoute path={`${match.url}total`} component={Total} />
      <ErrorBoundaryRoute path={`${match.url}communication`} component={Communication} />
      <ErrorBoundaryRoute path={`${match.url}attachment`} component={Attachment} />
      <ErrorBoundaryRoute path={`${match.url}payload`} component={Payload} />
      <ErrorBoundaryRoute path={`${match.url}note`} component={Note} />
      <ErrorBoundaryRoute path={`${match.url}communication-request`} component={CommunicationRequest} />
      <ErrorBoundaryRoute path={`${match.url}contact`} component={Contact} />
      <ErrorBoundaryRoute path={`${match.url}coverage`} component={Coverage} />
      <ErrorBoundaryRoute path={`${match.url}class-component`} component={ClassComponent} />
      <ErrorBoundaryRoute path={`${match.url}cost-to-beneficiary-component`} component={CostToBeneficiaryComponent} />
      <ErrorBoundaryRoute path={`${match.url}exemption-component`} component={ExemptionComponent} />
      <ErrorBoundaryRoute path={`${match.url}coverage-eligibility-request`} component={CoverageEligibilityRequest} />
      <ErrorBoundaryRoute path={`${match.url}coverage-eligibility-response`} component={CoverageEligibilityResponse} />
      <ErrorBoundaryRoute path={`${match.url}response-insurance`} component={ResponseInsurance} />
      <ErrorBoundaryRoute path={`${match.url}response-insurance-item`} component={ResponseInsuranceItem} />
      <ErrorBoundaryRoute path={`${match.url}insurance-benefit`} component={InsuranceBenefit} />
      <ErrorBoundaryRoute path={`${match.url}encounter`} component={Encounter} />
      <ErrorBoundaryRoute path={`${match.url}hospitalization`} component={Hospitalization} />
      <ErrorBoundaryRoute path={`${match.url}human-name`} component={HumanName} />
      <ErrorBoundaryRoute path={`${match.url}location`} component={Location} />
      <ErrorBoundaryRoute path={`${match.url}operation-outcome`} component={OperationOutcome} />
      <ErrorBoundaryRoute path={`${match.url}organization`} component={Organization} />
      <ErrorBoundaryRoute path={`${match.url}patient`} component={Patient} />
      <ErrorBoundaryRoute path={`${match.url}payment-notice`} component={PaymentNotice} />
      <ErrorBoundaryRoute path={`${match.url}payment-reconciliation`} component={PaymentReconciliation} />
      <ErrorBoundaryRoute path={`${match.url}reconciliation-detail-item`} component={ReconciliationDetailItem} />
      <ErrorBoundaryRoute path={`${match.url}practitioner`} component={Practitioner} />
      <ErrorBoundaryRoute path={`${match.url}practitioner-role`} component={PractitionerRole} />
      <ErrorBoundaryRoute path={`${match.url}reference-identifier`} component={ReferenceIdentifier} />
      <ErrorBoundaryRoute path={`${match.url}supporting-info`} component={SupportingInfo} />
      <ErrorBoundaryRoute path={`${match.url}quantity`} component={Quantity} />
      <ErrorBoundaryRoute path={`${match.url}task`} component={Task} />
      <ErrorBoundaryRoute path={`${match.url}task-input`} component={TaskInput} />
      <ErrorBoundaryRoute path={`${match.url}task-response`} component={TaskResponse} />
      <ErrorBoundaryRoute path={`${match.url}task-output`} component={TaskOutput} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
