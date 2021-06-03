import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IClaim } from 'app/shared/model/claim.model';
import { getEntities as getClaims } from 'app/entities/claim/claim.reducer';
import { getEntity, updateEntity, createEntity, reset } from './diagnosis.reducer';
import { IDiagnosis } from 'app/shared/model/diagnosis.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDiagnosisUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DiagnosisUpdate = (props: IDiagnosisUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { diagnosisEntity, claims, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/diagnosis');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getClaims();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...diagnosisEntity,
        ...values,
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
            <AvForm model={isNew ? {} : diagnosisEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="diagnosis-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="diagnosis-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="sequenceLabel" for="diagnosis-sequence">
                  <Translate contentKey="hcpNphiesPortalApp.diagnosis.sequence">Sequence</Translate>
                </Label>
                <AvField
                  id="diagnosis-sequence"
                  data-cy="sequence"
                  type="string"
                  className="form-control"
                  name="sequence"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="diagnosisLabel" for="diagnosis-diagnosis">
                  <Translate contentKey="hcpNphiesPortalApp.diagnosis.diagnosis">Diagnosis</Translate>
                </Label>
                <AvField
                  id="diagnosis-diagnosis"
                  data-cy="diagnosis"
                  type="text"
                  name="diagnosis"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="typeLabel" for="diagnosis-type">
                  <Translate contentKey="hcpNphiesPortalApp.diagnosis.type">Type</Translate>
                </Label>
                <AvInput
                  id="diagnosis-type"
                  data-cy="type"
                  type="select"
                  className="form-control"
                  name="type"
                  value={(!isNew && diagnosisEntity.type) || 'Admitting'}
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
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="onAdmissionLabel" for="diagnosis-onAdmission">
                  <Translate contentKey="hcpNphiesPortalApp.diagnosis.onAdmission">On Admission</Translate>
                </Label>
                <AvInput
                  id="diagnosis-onAdmission"
                  data-cy="onAdmission"
                  type="select"
                  className="form-control"
                  name="onAdmission"
                  value={(!isNew && diagnosisEntity.onAdmission) || 'Y'}
                >
                  <option value="Y">{translate('hcpNphiesPortalApp.DiagnosisOnAdmissionEnum.Y')}</option>
                  <option value="N">{translate('hcpNphiesPortalApp.DiagnosisOnAdmissionEnum.N')}</option>
                  <option value="U">{translate('hcpNphiesPortalApp.DiagnosisOnAdmissionEnum.U')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="diagnosis-claim">
                  <Translate contentKey="hcpNphiesPortalApp.diagnosis.claim">Claim</Translate>
                </Label>
                <AvInput id="diagnosis-claim" data-cy="claim" type="select" className="form-control" name="claimId">
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
              <Button tag={Link} id="cancel-save" to="/diagnosis" replace color="info">
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
  claims: storeState.claim.entities,
  diagnosisEntity: storeState.diagnosis.entity,
  loading: storeState.diagnosis.loading,
  updating: storeState.diagnosis.updating,
  updateSuccess: storeState.diagnosis.updateSuccess,
});

const mapDispatchToProps = {
  getClaims,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DiagnosisUpdate);
