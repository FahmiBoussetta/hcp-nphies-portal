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
import { IOrganization } from 'app/shared/model/organization.model';
import { getEntities as getOrganizations } from 'app/entities/organization/organization.reducer';
import { getEntity, updateEntity, createEntity, reset } from './payee.reducer';
import { IPayee } from 'app/shared/model/payee.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPayeeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PayeeUpdate = (props: IPayeeUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { payeeEntity, patients, organizations, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/payee');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPatients();
    props.getOrganizations();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...payeeEntity,
        ...values,
        partyPatient: patients.find(it => it.id.toString() === values.partyPatientId.toString()),
        partyOrganization: organizations.find(it => it.id.toString() === values.partyOrganizationId.toString()),
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
          <h2 id="hcpNphiesPortalApp.payee.home.createOrEditLabel" data-cy="PayeeCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.payee.home.createOrEditLabel">Create or edit a Payee</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : payeeEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="payee-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="payee-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="typeLabel" for="payee-type">
                  <Translate contentKey="hcpNphiesPortalApp.payee.type">Type</Translate>
                </Label>
                <AvInput
                  id="payee-type"
                  data-cy="type"
                  type="select"
                  className="form-control"
                  name="type"
                  value={(!isNew && payeeEntity.type) || 'Subscriber'}
                >
                  <option value="Subscriber">{translate('hcpNphiesPortalApp.PayeeTypeEnum.Subscriber')}</option>
                  <option value="Provider">{translate('hcpNphiesPortalApp.PayeeTypeEnum.Provider')}</option>
                  <option value="Other">{translate('hcpNphiesPortalApp.PayeeTypeEnum.Other')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="payee-partyPatient">
                  <Translate contentKey="hcpNphiesPortalApp.payee.partyPatient">Party Patient</Translate>
                </Label>
                <AvInput id="payee-partyPatient" data-cy="partyPatient" type="select" className="form-control" name="partyPatientId">
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
                <Label for="payee-partyOrganization">
                  <Translate contentKey="hcpNphiesPortalApp.payee.partyOrganization">Party Organization</Translate>
                </Label>
                <AvInput
                  id="payee-partyOrganization"
                  data-cy="partyOrganization"
                  type="select"
                  className="form-control"
                  name="partyOrganizationId"
                >
                  <option value="" key="0" />
                  {organizations
                    ? organizations.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/payee" replace color="info">
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
  organizations: storeState.organization.entities,
  payeeEntity: storeState.payee.entity,
  loading: storeState.payee.loading,
  updating: storeState.payee.updating,
  updateSuccess: storeState.payee.updateSuccess,
});

const mapDispatchToProps = {
  getPatients,
  getOrganizations,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PayeeUpdate);
