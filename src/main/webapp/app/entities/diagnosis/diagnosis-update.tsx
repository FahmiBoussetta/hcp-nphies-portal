import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IClaim } from 'app/shared/model/claim.model';
import { getEntities as getClaims } from 'app/entities/claim/claim.reducer';
import { getEntity, updateEntity, createEntity, reset } from './diagnosis.reducer';
import { IDiagnosis } from 'app/shared/model/diagnosis.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DiagnosisUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const claims = useAppSelector(state => state.claim.entities);
  const diagnosisEntity = useAppSelector(state => state.diagnosis.entity);
  const loading = useAppSelector(state => state.diagnosis.loading);
  const updating = useAppSelector(state => state.diagnosis.updating);
  const updateSuccess = useAppSelector(state => state.diagnosis.updateSuccess);

  const handleClose = () => {
    props.history.push('/diagnosis');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getClaims({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...diagnosisEntity,
      ...values,
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
      ? {}
      : {
          ...diagnosisEntity,
          type: 'Admitting',
          onAdmission: 'Y',
          claimId: diagnosisEntity?.claim?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.diagnosis.home.createOrEditLabel" data-cy="DiagnosisCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.diagnosis.home.createOrEditLabel">Create or edit a Diagnosis</Translate>
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
                  id="diagnosis-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.diagnosis.sequence')}
                id="diagnosis-sequence"
                name="sequence"
                data-cy="sequence"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.diagnosis.diagnosis')}
                id="diagnosis-diagnosis"
                name="diagnosis"
                data-cy="diagnosis"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.diagnosis.type')}
                id="diagnosis-type"
                name="type"
                data-cy="type"
                type="select"
              >
                <option value="Admitting">{translate('hcpNphiesPortalApp.DiagnosisTypeEnum.Admitting')}</option>
                <option value="Clinical">{translate('hcpNphiesPortalApp.DiagnosisTypeEnum.Clinical')}</option>
                <option value="Differential">{translate('hcpNphiesPortalApp.DiagnosisTypeEnum.Differential')}</option>
                <option value="Discharge">{translate('hcpNphiesPortalApp.DiagnosisTypeEnum.Discharge')}</option>
                <option value="Laboratory">{translate('hcpNphiesPortalApp.DiagnosisTypeEnum.Laboratory')}</option>
                <option value="Nursing">{translate('hcpNphiesPortalApp.DiagnosisTypeEnum.Nursing')}</option>
                <option value="Prenatal">{translate('hcpNphiesPortalApp.DiagnosisTypeEnum.Prenatal')}</option>
                <option value="Principal">{translate('hcpNphiesPortalApp.DiagnosisTypeEnum.Principal')}</option>
                <option value="Radiology">{translate('hcpNphiesPortalApp.DiagnosisTypeEnum.Radiology')}</option>
                <option value="Remote">{translate('hcpNphiesPortalApp.DiagnosisTypeEnum.Remote')}</option>
                <option value="Retrospective">{translate('hcpNphiesPortalApp.DiagnosisTypeEnum.Retrospective')}</option>
                <option value="Self">{translate('hcpNphiesPortalApp.DiagnosisTypeEnum.Self')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.diagnosis.onAdmission')}
                id="diagnosis-onAdmission"
                name="onAdmission"
                data-cy="onAdmission"
                type="select"
              >
                <option value="Y">{translate('hcpNphiesPortalApp.DiagnosisOnAdmissionEnum.Y')}</option>
                <option value="N">{translate('hcpNphiesPortalApp.DiagnosisOnAdmissionEnum.N')}</option>
                <option value="U">{translate('hcpNphiesPortalApp.DiagnosisOnAdmissionEnum.U')}</option>
              </ValidatedField>
              <ValidatedField
                id="diagnosis-claim"
                name="claimId"
                data-cy="claim"
                label={translate('hcpNphiesPortalApp.diagnosis.claim')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/diagnosis" replace color="info">
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

export default DiagnosisUpdate;
