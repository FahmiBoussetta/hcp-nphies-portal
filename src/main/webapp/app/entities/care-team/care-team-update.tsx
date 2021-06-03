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
import { IPractitionerRole } from 'app/shared/model/practitioner-role.model';
import { getEntities as getPractitionerRoles } from 'app/entities/practitioner-role/practitioner-role.reducer';
import { IClaim } from 'app/shared/model/claim.model';
import { getEntities as getClaims } from 'app/entities/claim/claim.reducer';
import { getEntity, updateEntity, createEntity, reset } from './care-team.reducer';
import { ICareTeam } from 'app/shared/model/care-team.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICareTeamUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CareTeamUpdate = (props: ICareTeamUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { careTeamEntity, practitioners, practitionerRoles, claims, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/care-team');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPractitioners();
    props.getPractitionerRoles();
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
        ...careTeamEntity,
        ...values,
        provider: practitioners.find(it => it.id.toString() === values.providerId.toString()),
        providerRole: practitionerRoles.find(it => it.id.toString() === values.providerRoleId.toString()),
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
          <h2 id="hcpNphiesPortalApp.careTeam.home.createOrEditLabel" data-cy="CareTeamCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.careTeam.home.createOrEditLabel">Create or edit a CareTeam</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : careTeamEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="care-team-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="care-team-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="sequenceLabel" for="care-team-sequence">
                  <Translate contentKey="hcpNphiesPortalApp.careTeam.sequence">Sequence</Translate>
                </Label>
                <AvField
                  id="care-team-sequence"
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
                <Label id="roleLabel" for="care-team-role">
                  <Translate contentKey="hcpNphiesPortalApp.careTeam.role">Role</Translate>
                </Label>
                <AvInput
                  id="care-team-role"
                  data-cy="role"
                  type="select"
                  className="form-control"
                  name="role"
                  value={(!isNew && careTeamEntity.role) || 'Primary'}
                >
                  <option value="Primary">{translate('hcpNphiesPortalApp.CareTeamRoleEnum.Primary')}</option>
                  <option value="Assist">{translate('hcpNphiesPortalApp.CareTeamRoleEnum.Assist')}</option>
                  <option value="Supervisor">{translate('hcpNphiesPortalApp.CareTeamRoleEnum.Supervisor')}</option>
                  <option value="Other">{translate('hcpNphiesPortalApp.CareTeamRoleEnum.Other')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="care-team-provider">
                  <Translate contentKey="hcpNphiesPortalApp.careTeam.provider">Provider</Translate>
                </Label>
                <AvInput id="care-team-provider" data-cy="provider" type="select" className="form-control" name="providerId">
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
                <Label for="care-team-providerRole">
                  <Translate contentKey="hcpNphiesPortalApp.careTeam.providerRole">Provider Role</Translate>
                </Label>
                <AvInput id="care-team-providerRole" data-cy="providerRole" type="select" className="form-control" name="providerRoleId">
                  <option value="" key="0" />
                  {practitionerRoles
                    ? practitionerRoles.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="care-team-claim">
                  <Translate contentKey="hcpNphiesPortalApp.careTeam.claim">Claim</Translate>
                </Label>
                <AvInput id="care-team-claim" data-cy="claim" type="select" className="form-control" name="claimId">
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
              <Button tag={Link} id="cancel-save" to="/care-team" replace color="info">
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
  practitionerRoles: storeState.practitionerRole.entities,
  claims: storeState.claim.entities,
  careTeamEntity: storeState.careTeam.entity,
  loading: storeState.careTeam.loading,
  updating: storeState.careTeam.updating,
  updateSuccess: storeState.careTeam.updateSuccess,
});

const mapDispatchToProps = {
  getPractitioners,
  getPractitionerRoles,
  getClaims,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CareTeamUpdate);
