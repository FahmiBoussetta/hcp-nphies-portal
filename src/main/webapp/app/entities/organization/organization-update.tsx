import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAddress } from 'app/shared/model/address.model';
import { getEntities as getAddresses } from 'app/entities/address/address.reducer';
import { getEntity, updateEntity, createEntity, reset } from './organization.reducer';
import { IOrganization } from 'app/shared/model/organization.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const OrganizationUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const addresses = useAppSelector(state => state.address.entities);
  const organizationEntity = useAppSelector(state => state.organization.entity);
  const loading = useAppSelector(state => state.organization.loading);
  const updating = useAppSelector(state => state.organization.updating);
  const updateSuccess = useAppSelector(state => state.organization.updateSuccess);

  const handleClose = () => {
    props.history.push('/organization');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAddresses({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...organizationEntity,
      ...values,
      address: addresses.find(it => it.id.toString() === values.addressId.toString()),
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
          ...organizationEntity,
          organizationType: 'Prov',
          addressId: organizationEntity?.address?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.organization.home.createOrEditLabel" data-cy="OrganizationCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.organization.home.createOrEditLabel">Create or edit a Organization</Translate>
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
                  id="organization-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.organization.guid')}
                id="organization-guid"
                name="guid"
                data-cy="guid"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.organization.forceId')}
                id="organization-forceId"
                name="forceId"
                data-cy="forceId"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.organization.organizationLicense')}
                id="organization-organizationLicense"
                name="organizationLicense"
                data-cy="organizationLicense"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.organization.baseUrl')}
                id="organization-baseUrl"
                name="baseUrl"
                data-cy="baseUrl"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.organization.organizationType')}
                id="organization-organizationType"
                name="organizationType"
                data-cy="organizationType"
                type="select"
              >
                <option value="Prov">{translate('hcpNphiesPortalApp.OrganizationTypeEnum.Prov')}</option>
                <option value="Dept">{translate('hcpNphiesPortalApp.OrganizationTypeEnum.Dept')}</option>
                <option value="Ins">{translate('hcpNphiesPortalApp.OrganizationTypeEnum.Ins')}</option>
                <option value="Pay">{translate('hcpNphiesPortalApp.OrganizationTypeEnum.Pay')}</option>
                <option value="Other">{translate('hcpNphiesPortalApp.OrganizationTypeEnum.Other')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.organization.name')}
                id="organization-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                id="organization-address"
                name="addressId"
                data-cy="address"
                label={translate('hcpNphiesPortalApp.organization.address')}
                type="select"
              >
                <option value="" key="0" />
                {addresses
                  ? addresses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/organization" replace color="info">
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

export default OrganizationUpdate;
