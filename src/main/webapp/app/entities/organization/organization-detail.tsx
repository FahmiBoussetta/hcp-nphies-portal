import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './organization.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOrganizationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OrganizationDetail = (props: IOrganizationDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { organizationEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="organizationDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.organization.detail.title">Organization</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{organizationEntity.id}</dd>
          <dt>
            <span id="guid">
              <Translate contentKey="hcpNphiesPortalApp.organization.guid">Guid</Translate>
            </span>
          </dt>
          <dd>{organizationEntity.guid}</dd>
          <dt>
            <span id="forceId">
              <Translate contentKey="hcpNphiesPortalApp.organization.forceId">Force Id</Translate>
            </span>
          </dt>
          <dd>{organizationEntity.forceId}</dd>
          <dt>
            <span id="organizationLicense">
              <Translate contentKey="hcpNphiesPortalApp.organization.organizationLicense">Organization License</Translate>
            </span>
          </dt>
          <dd>{organizationEntity.organizationLicense}</dd>
          <dt>
            <span id="baseUrl">
              <Translate contentKey="hcpNphiesPortalApp.organization.baseUrl">Base Url</Translate>
            </span>
          </dt>
          <dd>{organizationEntity.baseUrl}</dd>
          <dt>
            <span id="organizationType">
              <Translate contentKey="hcpNphiesPortalApp.organization.organizationType">Organization Type</Translate>
            </span>
          </dt>
          <dd>{organizationEntity.organizationType}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="hcpNphiesPortalApp.organization.name">Name</Translate>
            </span>
          </dt>
          <dd>{organizationEntity.name}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.organization.address">Address</Translate>
          </dt>
          <dd>{organizationEntity.address ? organizationEntity.address.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/organization" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/organization/${organizationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ organization }: IRootState) => ({
  organizationEntity: organization.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OrganizationDetail);
