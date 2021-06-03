import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPractitioner } from 'app/shared/model/practitioner.model';
import { getEntities as getPractitioners } from 'app/entities/practitioner/practitioner.reducer';
import { IOrganization } from 'app/shared/model/organization.model';
import { getEntities as getOrganizations } from 'app/entities/organization/organization.reducer';
import { getEntity, updateEntity, createEntity, reset } from './practitioner-role.reducer';
import { IPractitionerRole } from 'app/shared/model/practitioner-role.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPractitionerRoleUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PractitionerRoleUpdate = (props: IPractitionerRoleUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { practitionerRoleEntity, practitioners, organizations, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/practitioner-role');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPractitioners();
    props.getOrganizations();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.start = convertDateTimeToServer(values.start);
    values.end = convertDateTimeToServer(values.end);

    if (errors.length === 0) {
      const entity = {
        ...practitionerRoleEntity,
        ...values,
        practitioner: practitioners.find(it => it.id.toString() === values.practitionerId.toString()),
        organization: organizations.find(it => it.id.toString() === values.organizationId.toString()),
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
          <h2 id="hcpNphiesPortalApp.practitionerRole.home.createOrEditLabel" data-cy="PractitionerRoleCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.practitionerRole.home.createOrEditLabel">Create or edit a PractitionerRole</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : practitionerRoleEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="practitioner-role-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="practitioner-role-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="guidLabel" for="practitioner-role-guid">
                  <Translate contentKey="hcpNphiesPortalApp.practitionerRole.guid">Guid</Translate>
                </Label>
                <AvField id="practitioner-role-guid" data-cy="guid" type="text" name="guid" />
              </AvGroup>
              <AvGroup>
                <Label id="forceIdLabel" for="practitioner-role-forceId">
                  <Translate contentKey="hcpNphiesPortalApp.practitionerRole.forceId">Force Id</Translate>
                </Label>
                <AvField id="practitioner-role-forceId" data-cy="forceId" type="text" name="forceId" />
              </AvGroup>
              <AvGroup>
                <Label id="startLabel" for="practitioner-role-start">
                  <Translate contentKey="hcpNphiesPortalApp.practitionerRole.start">Start</Translate>
                </Label>
                <AvInput
                  id="practitioner-role-start"
                  data-cy="start"
                  type="datetime-local"
                  className="form-control"
                  name="start"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.practitionerRoleEntity.start)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="endLabel" for="practitioner-role-end">
                  <Translate contentKey="hcpNphiesPortalApp.practitionerRole.end">End</Translate>
                </Label>
                <AvInput
                  id="practitioner-role-end"
                  data-cy="end"
                  type="datetime-local"
                  className="form-control"
                  name="end"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.practitionerRoleEntity.end)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="practitioner-role-practitioner">
                  <Translate contentKey="hcpNphiesPortalApp.practitionerRole.practitioner">Practitioner</Translate>
                </Label>
                <AvInput
                  id="practitioner-role-practitioner"
                  data-cy="practitioner"
                  type="select"
                  className="form-control"
                  name="practitionerId"
                >
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
              <AvGroup>
                <Label for="practitioner-role-organization">
                  <Translate contentKey="hcpNphiesPortalApp.practitionerRole.organization">Organization</Translate>
                </Label>
                <AvInput
                  id="practitioner-role-organization"
                  data-cy="organization"
                  type="select"
                  className="form-control"
                  name="organizationId"
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
              <Button tag={Link} id="cancel-save" to="/practitioner-role" replace color="info">
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
  practitioners: storeState.practitioner.entities,
  organizations: storeState.organization.entities,
  practitionerRoleEntity: storeState.practitionerRole.entity,
  loading: storeState.practitionerRole.loading,
  updating: storeState.practitionerRole.updating,
  updateSuccess: storeState.practitionerRole.updateSuccess,
});

const mapDispatchToProps = {
  getPractitioners,
  getOrganizations,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PractitionerRoleUpdate);
