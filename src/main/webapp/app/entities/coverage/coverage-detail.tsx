import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './coverage.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CoverageDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const coverageEntity = useAppSelector(state => state.coverage.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="coverageDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.coverage.detail.title">Coverage</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{coverageEntity.id}</dd>
          <dt>
            <span id="guid">
              <Translate contentKey="hcpNphiesPortalApp.coverage.guid">Guid</Translate>
            </span>
          </dt>
          <dd>{coverageEntity.guid}</dd>
          <dt>
            <span id="forceId">
              <Translate contentKey="hcpNphiesPortalApp.coverage.forceId">Force Id</Translate>
            </span>
          </dt>
          <dd>{coverageEntity.forceId}</dd>
          <dt>
            <span id="coverageType">
              <Translate contentKey="hcpNphiesPortalApp.coverage.coverageType">Coverage Type</Translate>
            </span>
          </dt>
          <dd>{coverageEntity.coverageType}</dd>
          <dt>
            <span id="subscriberId">
              <Translate contentKey="hcpNphiesPortalApp.coverage.subscriberId">Subscriber Id</Translate>
            </span>
          </dt>
          <dd>{coverageEntity.subscriberId}</dd>
          <dt>
            <span id="dependent">
              <Translate contentKey="hcpNphiesPortalApp.coverage.dependent">Dependent</Translate>
            </span>
          </dt>
          <dd>{coverageEntity.dependent}</dd>
          <dt>
            <span id="relationShip">
              <Translate contentKey="hcpNphiesPortalApp.coverage.relationShip">Relation Ship</Translate>
            </span>
          </dt>
          <dd>{coverageEntity.relationShip}</dd>
          <dt>
            <span id="network">
              <Translate contentKey="hcpNphiesPortalApp.coverage.network">Network</Translate>
            </span>
          </dt>
          <dd>{coverageEntity.network}</dd>
          <dt>
            <span id="subrogation">
              <Translate contentKey="hcpNphiesPortalApp.coverage.subrogation">Subrogation</Translate>
            </span>
          </dt>
          <dd>{coverageEntity.subrogation ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.coverage.subscriberPatient">Subscriber Patient</Translate>
          </dt>
          <dd>{coverageEntity.subscriberPatient ? coverageEntity.subscriberPatient.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.coverage.beneficiary">Beneficiary</Translate>
          </dt>
          <dd>{coverageEntity.beneficiary ? coverageEntity.beneficiary.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.coverage.payor">Payor</Translate>
          </dt>
          <dd>{coverageEntity.payor ? coverageEntity.payor.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/coverage" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/coverage/${coverageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CoverageDetail;
