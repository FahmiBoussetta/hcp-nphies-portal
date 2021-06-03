import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAddress } from 'app/shared/model/address.model';
import { getEntities as getAddresses } from 'app/entities/address/address.reducer';
import { getEntity, updateEntity, createEntity, reset } from './organization.reducer';
import { IOrganization } from 'app/shared/model/organization.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOrganizationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OrganizationUpdate = (props: IOrganizationUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { organizationEntity, addresses, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/organization');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getAddresses();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...organizationEntity,
        ...values,
        address: addresses.find(it => it.id.toString() === values.addressId.toString()),
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
            <AvForm model={isNew ? {} : organizationEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="organization-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="organization-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="guidLabel" for="organization-guid">
                  <Translate contentKey="hcpNphiesPortalApp.organization.guid">Guid</Translate>
                </Label>
                <AvField id="organization-guid" data-cy="guid" type="text" name="guid" />
              </AvGroup>
              <AvGroup>
                <Label id="forceIdLabel" for="organization-forceId">
                  <Translate contentKey="hcpNphiesPortalApp.organization.forceId">Force Id</Translate>
                </Label>
                <AvField id="organization-forceId" data-cy="forceId" type="text" name="forceId" />
              </AvGroup>
              <AvGroup>
                <Label id="organizationLicenseLabel" for="organization-organizationLicense">
                  <Translate contentKey="hcpNphiesPortalApp.organization.organizationLicense">Organization License</Translate>
                </Label>
                <AvField id="organization-organizationLicense" data-cy="organizationLicense" type="text" name="organizationLicense" />
              </AvGroup>
              <AvGroup>
                <Label id="baseUrlLabel" for="organization-baseUrl">
                  <Translate contentKey="hcpNphiesPortalApp.organization.baseUrl">Base Url</Translate>
                </Label>
                <AvField id="organization-baseUrl" data-cy="baseUrl" type="text" name="baseUrl" />
              </AvGroup>
              <AvGroup>
                <Label id="organizationTypeLabel" for="organization-organizationType">
                  <Translate contentKey="hcpNphiesPortalApp.organization.organizationType">Organization Type</Translate>
                </Label>
                <AvInput
                  id="organization-organizationType"
                  data-cy="organizationType"
                  type="select"
                  className="form-control"
                  name="organizationType"
                  value={(!isNew && organizationEntity.organizationType) || 'Prov'}
                >
                  <option value="Prov">{translate('hcpNphiesPortalApp.OrganizationTypeEnum.Prov')}</option>
                  <option value="Dept">{translate('hcpNphiesPortalApp.OrganizationTypeEnum.Dept')}</option>
                  <option value="Ins">{translate('hcpNphiesPortalApp.OrganizationTypeEnum.Ins')}</option>
                  <option value="Pay">{translate('hcpNphiesPortalApp.OrganizationTypeEnum.Pay')}</option>
                  <option value="Other">{translate('hcpNphiesPortalApp.OrganizationTypeEnum.Other')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="nameLabel" for="organization-name">
                  <Translate contentKey="hcpNphiesPortalApp.organization.name">Name</Translate>
                </Label>
                <AvField id="organization-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label for="organization-address">
                  <Translate contentKey="hcpNphiesPortalApp.organization.address">Address</Translate>
                </Label>
                <AvInput id="organization-address" data-cy="address" type="select" className="form-control" name="addressId">
                  <option value="" key="0" />
                  {addresses
                    ? addresses.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/organization" replace color="info">
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
  addresses: storeState.address.entities,
  organizationEntity: storeState.organization.entity,
  loading: storeState.organization.loading,
  updating: storeState.organization.updating,
  updateSuccess: storeState.organization.updateSuccess,
});

const mapDispatchToProps = {
  getAddresses,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OrganizationUpdate);
