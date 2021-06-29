import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

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
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SupportingInfoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const quantities = useAppSelector(state => state.quantity.entities);
  const attachments = useAppSelector(state => state.attachment.entities);
  const referenceIdentifiers = useAppSelector(state => state.referenceIdentifier.entities);
  const claims = useAppSelector(state => state.claim.entities);
  const supportingInfoEntity = useAppSelector(state => state.supportingInfo.entity);
  const loading = useAppSelector(state => state.supportingInfo.loading);
  const updating = useAppSelector(state => state.supportingInfo.updating);
  const updateSuccess = useAppSelector(state => state.supportingInfo.updateSuccess);

  const handleClose = () => {
    props.history.push('/supporting-info');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getQuantities({}));
    dispatch(getAttachments({}));
    dispatch(getReferenceIdentifiers({}));
    dispatch(getClaims({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.timing = convertDateTimeToServer(values.timing);
    values.timingEnd = convertDateTimeToServer(values.timingEnd);

    const entity = {
      ...supportingInfoEntity,
      ...values,
      valueQuantity: quantities.find(it => it.id.toString() === values.valueQuantityId.toString()),
      valueAttachment: attachments.find(it => it.id.toString() === values.valueAttachmentId.toString()),
      valueReference: referenceIdentifiers.find(it => it.id.toString() === values.valueReferenceId.toString()),
      claim: claims.find(it => it.id.toString() === values.claimId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          timing: displayDefaultDateTime(),
          timingEnd: displayDefaultDateTime(),
        }
      : {
          ...supportingInfoEntity,
          category: 'Info',
          codeVisit: 'New_visit',
          codeFdiOral: 'N11',
          timing: convertDateTimeFromServer(supportingInfoEntity.timing),
          timingEnd: convertDateTimeFromServer(supportingInfoEntity.timingEnd),
          reason: 'Missing_info',
          reasonMissingTooth: 'E',
          valueQuantityId: supportingInfoEntity?.valueQuantity?.id,
          valueAttachmentId: supportingInfoEntity?.valueAttachment?.id,
          valueReferenceId: supportingInfoEntity?.valueReference?.id,
          claimId: supportingInfoEntity?.claim?.id,
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
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="supporting-info-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.supportingInfo.sequence')}
                id="supporting-info-sequence"
                name="sequence"
                data-cy="sequence"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.supportingInfo.codeLOINC')}
                id="supporting-info-codeLOINC"
                name="codeLOINC"
                data-cy="codeLOINC"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.supportingInfo.category')}
                id="supporting-info-category"
                name="category"
                data-cy="category"
                type="select"
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
                <option value="EmploymentImpacted">{translate('hcpNphiesPortalApp.SupportingInfoCategoryEnum.EmploymentImpacted')}</option>
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
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.supportingInfo.codeVisit')}
                id="supporting-info-codeVisit"
                name="codeVisit"
                data-cy="codeVisit"
                type="select"
              >
                <option value="New_visit">{translate('hcpNphiesPortalApp.SupportingInfoCodeVisitEnum.New_visit')}</option>
                <option value="Follow_up">{translate('hcpNphiesPortalApp.SupportingInfoCodeVisitEnum.Follow_up')}</option>
                <option value="Refill">{translate('hcpNphiesPortalApp.SupportingInfoCodeVisitEnum.Refill')}</option>
                <option value="Walk_in">{translate('hcpNphiesPortalApp.SupportingInfoCodeVisitEnum.Walk_in')}</option>
                <option value="Referral">{translate('hcpNphiesPortalApp.SupportingInfoCodeVisitEnum.Referral')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.supportingInfo.codeFdiOral')}
                id="supporting-info-codeFdiOral"
                name="codeFdiOral"
                data-cy="codeFdiOral"
                type="select"
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
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.supportingInfo.timing')}
                id="supporting-info-timing"
                name="timing"
                data-cy="timing"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.supportingInfo.timingEnd')}
                id="supporting-info-timingEnd"
                name="timingEnd"
                data-cy="timingEnd"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.supportingInfo.valueBoolean')}
                id="supporting-info-valueBoolean"
                name="valueBoolean"
                data-cy="valueBoolean"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.supportingInfo.valueString')}
                id="supporting-info-valueString"
                name="valueString"
                data-cy="valueString"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.supportingInfo.reason')}
                id="supporting-info-reason"
                name="reason"
                data-cy="reason"
                type="select"
              >
                <option value="Missing_info">{translate('hcpNphiesPortalApp.SupportingInfoReasonEnum.Missing_info')}</option>
                <option value="Missing_attach">{translate('hcpNphiesPortalApp.SupportingInfoReasonEnum.Missing_attach')}</option>
                <option value="Info_Correct">{translate('hcpNphiesPortalApp.SupportingInfoReasonEnum.Info_Correct')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.supportingInfo.reasonMissingTooth')}
                id="supporting-info-reasonMissingTooth"
                name="reasonMissingTooth"
                data-cy="reasonMissingTooth"
                type="select"
              >
                <option value="E">{translate('hcpNphiesPortalApp.SupportingInfoReasonMissingToothEnum.E')}</option>
                <option value="C">{translate('hcpNphiesPortalApp.SupportingInfoReasonMissingToothEnum.C')}</option>
                <option value="U">{translate('hcpNphiesPortalApp.SupportingInfoReasonMissingToothEnum.U')}</option>
                <option value="O">{translate('hcpNphiesPortalApp.SupportingInfoReasonMissingToothEnum.O')}</option>
              </ValidatedField>
              <ValidatedField
                id="supporting-info-valueQuantity"
                name="valueQuantityId"
                data-cy="valueQuantity"
                label={translate('hcpNphiesPortalApp.supportingInfo.valueQuantity')}
                type="select"
              >
                <option value="" key="0" />
                {quantities
                  ? quantities.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="supporting-info-valueAttachment"
                name="valueAttachmentId"
                data-cy="valueAttachment"
                label={translate('hcpNphiesPortalApp.supportingInfo.valueAttachment')}
                type="select"
              >
                <option value="" key="0" />
                {attachments
                  ? attachments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="supporting-info-valueReference"
                name="valueReferenceId"
                data-cy="valueReference"
                label={translate('hcpNphiesPortalApp.supportingInfo.valueReference')}
                type="select"
              >
                <option value="" key="0" />
                {referenceIdentifiers
                  ? referenceIdentifiers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="supporting-info-claim"
                name="claimId"
                data-cy="claim"
                label={translate('hcpNphiesPortalApp.supportingInfo.claim')}
                type="select"
              >
                <option value="" key="0" />
                {claims
                  ? claims.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/supporting-info" replace color="info">
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
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default SupportingInfoUpdate;
