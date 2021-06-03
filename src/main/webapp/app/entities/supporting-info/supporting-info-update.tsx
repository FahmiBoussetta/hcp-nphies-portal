import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IQuantity } from 'app/shared/model/quantity.model';
import { getEntities as getQuantities } from 'app/entities/quantity/quantity.reducer';
import { IAttachment } from 'app/shared/model/attachment.model';
import { getEntities as getAttachments } from 'app/entities/attachment/attachment.reducer';
import { IReferenceIdentifier } from 'app/shared/model/reference-identifier.model';
import { getEntities as getReferenceIdentifiers } from 'app/entities/reference-identifier/reference-identifier.reducer';
import { IClaim } from 'app/shared/model/claim.model';
import { getEntities as getClaims } from 'app/entities/claim/claim.reducer';
import { getEntity, updateEntity, createEntity, reset } from './supporting-info.reducer';
import { ISupportingInfo } from 'app/shared/model/supporting-info.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISupportingInfoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SupportingInfoUpdate = (props: ISupportingInfoUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { supportingInfoEntity, quantities, attachments, referenceIdentifiers, claims, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/supporting-info');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getQuantities();
    props.getAttachments();
    props.getReferenceIdentifiers();
    props.getClaims();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.timing = convertDateTimeToServer(values.timing);
    values.timingEnd = convertDateTimeToServer(values.timingEnd);

    if (errors.length === 0) {
      const entity = {
        ...supportingInfoEntity,
        ...values,
        valueQuantity: quantities.find(it => it.id.toString() === values.valueQuantityId.toString()),
        valueAttachment: attachments.find(it => it.id.toString() === values.valueAttachmentId.toString()),
        valueReference: referenceIdentifiers.find(it => it.id.toString() === values.valueReferenceId.toString()),
        claim: claims.find(it => it.id.toString() === values.claimId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.supportingInfo.home.createOrEditLabel" data-cy="SupportingInfoCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.supportingInfo.home.createOrEditLabel">Create or edit a SupportingInfo</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : supportingInfoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="supporting-info-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="supporting-info-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="sequenceLabel" for="supporting-info-sequence">
                  <Translate contentKey="hcpNphiesPortalApp.supportingInfo.sequence">Sequence</Translate>
                </Label>
                <AvField id="supporting-info-sequence" data-cy="sequence" type="string" className="form-control" name="sequence" />
              </AvGroup>
              <AvGroup>
                <Label id="codeLOINCLabel" for="supporting-info-codeLOINC">
                  <Translate contentKey="hcpNphiesPortalApp.supportingInfo.codeLOINC">Code LOINC</Translate>
                </Label>
                <AvField id="supporting-info-codeLOINC" data-cy="codeLOINC" type="text" name="codeLOINC" />
              </AvGroup>
              <AvGroup>
                <Label id="categoryLabel" for="supporting-info-category">
                  <Translate contentKey="hcpNphiesPortalApp.supportingInfo.category">Category</Translate>
                </Label>
                <AvInput
                  id="supporting-info-category"
                  data-cy="category"
                  type="select"
                  className="form-control"
                  name="category"
                  value={(!isNew && supportingInfoEntity.category) || 'Info'}
                >
                  <option value="Info">{translate('hcpNphiesPortalApp.SupportingInfoCategoryEnum.Info')}</option>
                  <option value="Discharge">{translate('hcpNphiesPortalApp.SupportingInfoCategoryEnum.Discharge')}</option>
                  <option value="Onset">{translate('hcpNphiesPortalApp.SupportingInfoCategoryEnum.Onset')}</option>
                  <option value="Related">{translate('hcpNphiesPortalApp.SupportingInfoCategoryEnum.Related')}</option>
                  <option value="Exception">{translate('hcpNphiesPortalApp.SupportingInfoCategoryEnum.Exception')}</option>
                  <option value="Material">{translate('hcpNphiesPortalApp.SupportingInfoCategoryEnum.Material')}</option>
                  <option value="Attachment">{translate('hcpNphiesPortalApp.SupportingInfoCategoryEnum.Attachment')}</option>
                  <option value="Missingtooth">{translate('hcpNphiesPortalApp.SupportingInfoCategoryEnum.Missingtooth')}</option>
                  <option value="Prosthesis">{translate('hcpNphiesPortalApp.SupportingInfoCategoryEnum.Prosthesis')}</option>
                  <option value="Other">{translate('hcpNphiesPortalApp.SupportingInfoCategoryEnum.Other')}</option>
                  <option value="Hospitalized">{translate('hcpNphiesPortalApp.SupportingInfoCategoryEnum.Hospitalized')}</option>
                  <option value="EmploymentImpacted">
                    {translate('hcpNphiesPortalApp.SupportingInfoCategoryEnum.EmploymentImpacted')}
                  </option>
                  <option value="External_Cause">{translate('hcpNphiesPortalApp.SupportingInfoCategoryEnum.External_Cause')}</option>
                  <option value="Patient_Reason_for_Visit">
                    {translate('hcpNphiesPortalApp.SupportingInfoCategoryEnum.Patient_Reason_for_Visit')}
                  </option>
                  <option value="Lab_test">{translate('hcpNphiesPortalApp.SupportingInfoCategoryEnum.Lab_test')}</option>
                  <option value="Reason_for_Visit">{translate('hcpNphiesPortalApp.SupportingInfoCategoryEnum.Reason_for_Visit')}</option>
                  <option value="Days_Supply">{translate('hcpNphiesPortalApp.SupportingInfoCategoryEnum.Days_Supply')}</option>
                  <option value="Vital_Sign_Weight">{translate('hcpNphiesPortalApp.SupportingInfoCategoryEnum.Vital_Sign_Weight')}</option>
                  <option value="Vital_Sign_Systolic">
                    {translate('hcpNphiesPortalApp.SupportingInfoCategoryEnum.Vital_Sign_Systolic')}
                  </option>
                  <option value="Vital_Sign_Diastolic">
                    {translate('hcpNphiesPortalApp.SupportingInfoCategoryEnum.Vital_Sign_Diastolic')}
                  </option>
                  <option value="Icu_hours">{translate('hcpNphiesPortalApp.SupportingInfoCategoryEnum.Icu_hours')}</option>
                  <option value="Ventilation_hours">{translate('hcpNphiesPortalApp.SupportingInfoCategoryEnum.Ventilation_hours')}</option>
                  <option value="Vital_Sign_Height">{translate('hcpNphiesPortalApp.SupportingInfoCategoryEnum.Vital_Sign_Height')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="codeVisitLabel" for="supporting-info-codeVisit">
                  <Translate contentKey="hcpNphiesPortalApp.supportingInfo.codeVisit">Code Visit</Translate>
                </Label>
                <AvInput
                  id="supporting-info-codeVisit"
                  data-cy="codeVisit"
                  type="select"
                  className="form-control"
                  name="codeVisit"
                  value={(!isNew && supportingInfoEntity.codeVisit) || 'New_visit'}
                >
                  <option value="New_visit">{translate('hcpNphiesPortalApp.SupportingInfoCodeVisitEnum.New_visit')}</option>
                  <option value="Follow_up">{translate('hcpNphiesPortalApp.SupportingInfoCodeVisitEnum.Follow_up')}</option>
                  <option value="Refill">{translate('hcpNphiesPortalApp.SupportingInfoCodeVisitEnum.Refill')}</option>
                  <option value="Walk_in">{translate('hcpNphiesPortalApp.SupportingInfoCodeVisitEnum.Walk_in')}</option>
                  <option value="Referral">{translate('hcpNphiesPortalApp.SupportingInfoCodeVisitEnum.Referral')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="codeFdiOralLabel" for="supporting-info-codeFdiOral">
                  <Translate contentKey="hcpNphiesPortalApp.supportingInfo.codeFdiOral">Code Fdi Oral</Translate>
                </Label>
                <AvInput
                  id="supporting-info-codeFdiOral"
                  data-cy="codeFdiOral"
                  type="select"
                  className="form-control"
                  name="codeFdiOral"
                  value={(!isNew && supportingInfoEntity.codeFdiOral) || 'N11'}
                >
                  <option value="N11">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N11')}</option>
                  <option value="N12">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N12')}</option>
                  <option value="N13">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N13')}</option>
                  <option value="N14">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N14')}</option>
                  <option value="N15">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N15')}</option>
                  <option value="N16">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N16')}</option>
                  <option value="N17">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N17')}</option>
                  <option value="N18">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N18')}</option>
                  <option value="N21">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N21')}</option>
                  <option value="N22">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N22')}</option>
                  <option value="N23">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N23')}</option>
                  <option value="N24">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N24')}</option>
                  <option value="N25">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N25')}</option>
                  <option value="N26">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N26')}</option>
                  <option value="N27">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N27')}</option>
                  <option value="N28">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N28')}</option>
                  <option value="N31">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N31')}</option>
                  <option value="N32">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N32')}</option>
                  <option value="N33">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N33')}</option>
                  <option value="N34">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N34')}</option>
                  <option value="N35">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N35')}</option>
                  <option value="N36">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N36')}</option>
                  <option value="N37">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N37')}</option>
                  <option value="N38">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N38')}</option>
                  <option value="N41">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N41')}</option>
                  <option value="N42">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N42')}</option>
                  <option value="N43">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N43')}</option>
                  <option value="N44">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N44')}</option>
                  <option value="N45">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N45')}</option>
                  <option value="N46">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N46')}</option>
                  <option value="N47">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N47')}</option>
                  <option value="N48">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N48')}</option>
                  <option value="N51">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N51')}</option>
                  <option value="N52">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N52')}</option>
                  <option value="N53">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N53')}</option>
                  <option value="N54">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N54')}</option>
                  <option value="N55">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N55')}</option>
                  <option value="N61">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N61')}</option>
                  <option value="N62">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N62')}</option>
                  <option value="N63">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N63')}</option>
                  <option value="N64">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N64')}</option>
                  <option value="N65">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N65')}</option>
                  <option value="N71">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N71')}</option>
                  <option value="N72">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N72')}</option>
                  <option value="N73">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N73')}</option>
                  <option value="N74">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N74')}</option>
                  <option value="N75">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N75')}</option>
                  <option value="N81">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N81')}</option>
                  <option value="N82">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N82')}</option>
                  <option value="N83">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N83')}</option>
                  <option value="N84">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N84')}</option>
                  <option value="N85">{translate('hcpNphiesPortalApp.SupportingInfoCodeFdiEnum.N85')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="timingLabel" for="supporting-info-timing">
                  <Translate contentKey="hcpNphiesPortalApp.supportingInfo.timing">Timing</Translate>
                </Label>
                <AvInput
                  id="supporting-info-timing"
                  data-cy="timing"
                  type="datetime-local"
                  className="form-control"
                  name="timing"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.supportingInfoEntity.timing)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="timingEndLabel" for="supporting-info-timingEnd">
                  <Translate contentKey="hcpNphiesPortalApp.supportingInfo.timingEnd">Timing End</Translate>
                </Label>
                <AvInput
                  id="supporting-info-timingEnd"
                  data-cy="timingEnd"
                  type="datetime-local"
                  className="form-control"
                  name="timingEnd"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.supportingInfoEntity.timingEnd)}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="valueBooleanLabel">
                  <AvInput
                    id="supporting-info-valueBoolean"
                    data-cy="valueBoolean"
                    type="checkbox"
                    className="form-check-input"
                    name="valueBoolean"
                  />
                  <Translate contentKey="hcpNphiesPortalApp.supportingInfo.valueBoolean">Value Boolean</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="valueStringLabel" for="supporting-info-valueString">
                  <Translate contentKey="hcpNphiesPortalApp.supportingInfo.valueString">Value String</Translate>
                </Label>
                <AvField id="supporting-info-valueString" data-cy="valueString" type="text" name="valueString" />
              </AvGroup>
              <AvGroup>
                <Label id="reasonLabel" for="supporting-info-reason">
                  <Translate contentKey="hcpNphiesPortalApp.supportingInfo.reason">Reason</Translate>
                </Label>
                <AvInput
                  id="supporting-info-reason"
                  data-cy="reason"
                  type="select"
                  className="form-control"
                  name="reason"
                  value={(!isNew && supportingInfoEntity.reason) || 'Missing_info'}
                >
                  <option value="Missing_info">{translate('hcpNphiesPortalApp.SupportingInfoReasonEnum.Missing_info')}</option>
                  <option value="Missing_attach">{translate('hcpNphiesPortalApp.SupportingInfoReasonEnum.Missing_attach')}</option>
                  <option value="Info_Correct">{translate('hcpNphiesPortalApp.SupportingInfoReasonEnum.Info_Correct')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="reasonMissingToothLabel" for="supporting-info-reasonMissingTooth">
                  <Translate contentKey="hcpNphiesPortalApp.supportingInfo.reasonMissingTooth">Reason Missing Tooth</Translate>
                </Label>
                <AvInput
                  id="supporting-info-reasonMissingTooth"
                  data-cy="reasonMissingTooth"
                  type="select"
                  className="form-control"
                  name="reasonMissingTooth"
                  value={(!isNew && supportingInfoEntity.reasonMissingTooth) || 'E'}
                >
                  <option value="E">{translate('hcpNphiesPortalApp.SupportingInfoReasonMissingToothEnum.E')}</option>
                  <option value="C">{translate('hcpNphiesPortalApp.SupportingInfoReasonMissingToothEnum.C')}</option>
                  <option value="U">{translate('hcpNphiesPortalApp.SupportingInfoReasonMissingToothEnum.U')}</option>
                  <option value="O">{translate('hcpNphiesPortalApp.SupportingInfoReasonMissingToothEnum.O')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="supporting-info-valueQuantity">
                  <Translate contentKey="hcpNphiesPortalApp.supportingInfo.valueQuantity">Value Quantity</Translate>
                </Label>
                <AvInput
                  id="supporting-info-valueQuantity"
                  data-cy="valueQuantity"
                  type="select"
                  className="form-control"
                  name="valueQuantityId"
                >
                  <option value="" key="0" />
                  {quantities
                    ? quantities.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="supporting-info-valueAttachment">
                  <Translate contentKey="hcpNphiesPortalApp.supportingInfo.valueAttachment">Value Attachment</Translate>
                </Label>
                <AvInput
                  id="supporting-info-valueAttachment"
                  data-cy="valueAttachment"
                  type="select"
                  className="form-control"
                  name="valueAttachmentId"
                >
                  <option value="" key="0" />
                  {attachments
                    ? attachments.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="supporting-info-valueReference">
                  <Translate contentKey="hcpNphiesPortalApp.supportingInfo.valueReference">Value Reference</Translate>
                </Label>
                <AvInput
                  id="supporting-info-valueReference"
                  data-cy="valueReference"
                  type="select"
                  className="form-control"
                  name="valueReferenceId"
                >
                  <option value="" key="0" />
                  {referenceIdentifiers
                    ? referenceIdentifiers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="supporting-info-claim">
                  <Translate contentKey="hcpNphiesPortalApp.supportingInfo.claim">Claim</Translate>
                </Label>
                <AvInput id="supporting-info-claim" data-cy="claim" type="select" className="form-control" name="claimId">
                  <option value="" key="0" />
                  {claims
                    ? claims.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/supporting-info" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  quantities: storeState.quantity.entities,
  attachments: storeState.attachment.entities,
  referenceIdentifiers: storeState.referenceIdentifier.entities,
  claims: storeState.claim.entities,
  supportingInfoEntity: storeState.supportingInfo.entity,
  loading: storeState.supportingInfo.loading,
  updating: storeState.supportingInfo.updating,
  updateSuccess: storeState.supportingInfo.updateSuccess,
});

const mapDispatchToProps = {
  getQuantities,
  getAttachments,
  getReferenceIdentifiers,
  getClaims,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SupportingInfoUpdate);
