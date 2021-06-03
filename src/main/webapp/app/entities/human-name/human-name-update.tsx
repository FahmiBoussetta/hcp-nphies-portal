import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPatient } from 'app/shared/model/patient.model';
import { getEntities as getPatients } from 'app/entities/patient/patient.reducer';
import { IPractitioner } from 'app/shared/model/practitioner.model';
import { getEntities as getPractitioners } from 'app/entities/practitioner/practitioner.reducer';
import { getEntity, updateEntity, createEntity, reset } from './human-name.reducer';
import { IHumanName } from 'app/shared/model/human-name.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IHumanNameUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const HumanNameUpdate = (props: IHumanNameUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { humanNameEntity, patients, practitioners, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/human-name');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPatients();
    props.getPractitioners();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...humanNameEntity,
        ...values,
        patient: patients.find(it => it.id.toString() === values.patientId.toString()),
        practitioner: practitioners.find(it => it.id.toString() === values.practitionerId.toString()),
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
          <h2 id="hcpNphiesPortalApp.humanName.home.createOrEditLabel" data-cy="HumanNameCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.humanName.home.createOrEditLabel">Create or edit a HumanName</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : humanNameEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="human-name-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="human-name-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="familyLabel" for="human-name-family">
                  <Translate contentKey="hcpNphiesPortalApp.humanName.family">Family</Translate>
                </Label>
                <AvField
                  id="human-name-family"
                  data-cy="family"
                  type="text"
                  name="family"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="human-name-patient">
                  <Translate contentKey="hcpNphiesPortalApp.humanName.patient">Patient</Translate>
                </Label>
                <AvInput id="human-name-patient" data-cy="patient" type="select" className="form-control" name="patientId">
                  <option value="" key="0" />
                  {patients
                    ? patients.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="human-name-practitioner">
                  <Translate contentKey="hcpNphiesPortalApp.humanName.practitioner">Practitioner</Translate>
                </Label>
                <AvInput id="human-name-practitioner" data-cy="practitioner" type="select" className="form-control" name="practitionerId">
                  <option value="" key="0" />
                  {practitioners
                    ? practitioners.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/human-name" replace color="info">
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
  patients: storeState.patient.entities,
  practitioners: storeState.practitioner.entities,
  humanNameEntity: storeState.humanName.entity,
  loading: storeState.humanName.loading,
  updating: storeState.humanName.updating,
  updateSuccess: storeState.humanName.updateSuccess,
});

const mapDispatchToProps = {
  getPatients,
  getPractitioners,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(HumanNameUpdate);
