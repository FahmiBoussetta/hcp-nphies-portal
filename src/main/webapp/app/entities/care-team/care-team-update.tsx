import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

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
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CareTeamUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const practitioners = useAppSelector(state => state.practitioner.entities);
  const practitionerRoles = useAppSelector(state => state.practitionerRole.entities);
  const claims = useAppSelector(state => state.claim.entities);
  const careTeamEntity = useAppSelector(state => state.careTeam.entity);
  const loading = useAppSelector(state => state.careTeam.loading);
  const updating = useAppSelector(state => state.careTeam.updating);
  const updateSuccess = useAppSelector(state => state.careTeam.updateSuccess);

  const handleClose = () => {
    props.history.push('/care-team');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getPractitioners({}));
    dispatch(getPractitionerRoles({}));
    dispatch(getClaims({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...careTeamEntity,
      ...values,
      provider: practitioners.find(it => it.id.toString() === values.providerId.toString()),
      providerRole: practitionerRoles.find(it => it.id.toString() === values.providerRoleId.toString()),
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
          ...careTeamEntity,
          role: 'Primary',
          providerId: careTeamEntity?.provider?.id,
          providerRoleId: careTeamEntity?.providerRole?.id,
          claimId: careTeamEntity?.claim?.id,
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
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="care-team-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.careTeam.sequence')}
                id="care-team-sequence"
                name="sequence"
                data-cy="sequence"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.careTeam.role')}
                id="care-team-role"
                name="role"
                data-cy="role"
                type="select"
              >
                <option value="Primary">{translate('hcpNphiesPortalApp.CareTeamRoleEnum.Primary')}</option>
                <option value="Assist">{translate('hcpNphiesPortalApp.CareTeamRoleEnum.Assist')}</option>
                <option value="Supervisor">{translate('hcpNphiesPortalApp.CareTeamRoleEnum.Supervisor')}</option>
                <option value="Other">{translate('hcpNphiesPortalApp.CareTeamRoleEnum.Other')}</option>
              </ValidatedField>
              <ValidatedField
                id="care-team-provider"
                name="providerId"
                data-cy="provider"
                label={translate('hcpNphiesPortalApp.careTeam.provider')}
                type="select"
              >
                <option value="" key="0" />
                {practitioners
                  ? practitioners.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="care-team-providerRole"
                name="providerRoleId"
                data-cy="providerRole"
                label={translate('hcpNphiesPortalApp.careTeam.providerRole')}
                type="select"
              >
                <option value="" key="0" />
                {practitionerRoles
                  ? practitionerRoles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="care-team-claim"
                name="claimId"
                data-cy="claim"
                label={translate('hcpNphiesPortalApp.careTeam.claim')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/care-team" replace color="info">
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

export default CareTeamUpdate;
