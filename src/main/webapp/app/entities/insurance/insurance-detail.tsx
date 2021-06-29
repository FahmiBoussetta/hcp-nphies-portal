import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './insurance.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const InsuranceDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const insuranceEntity = useAppSelector(state => state.insurance.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="insuranceDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.insurance.detail.title">Insurance</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{insuranceEntity.id}</dd>
          <dt>
            <span id="sequence">
              <Translate contentKey="hcpNphiesPortalApp.insurance.sequence">Sequence</Translate>
            </span>
          </dt>
          <dd>{insuranceEntity.sequence}</dd>
          <dt>
            <span id="focal">
              <Translate contentKey="hcpNphiesPortalApp.insurance.focal">Focal</Translate>
            </span>
          </dt>
          <dd>{insuranceEntity.focal ? 'true' : 'false'}</dd>
          <dt>
            <span id="preAuthRef">
              <Translate contentKey="hcpNphiesPortalApp.insurance.preAuthRef">Pre Auth Ref</Translate>
            </span>
          </dt>
          <dd>{insuranceEntity.preAuthRef}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.insurance.coverage">Coverage</Translate>
          </dt>
          <dd>{insuranceEntity.coverage ? insuranceEntity.coverage.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.insurance.claimResponse">Claim Response</Translate>
          </dt>
          <dd>{insuranceEntity.claimResponse ? insuranceEntity.claimResponse.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.insurance.claim">Claim</Translate>
          </dt>
          <dd>{insuranceEntity.claim ? insuranceEntity.claim.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/insurance" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/insurance/${insuranceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InsuranceDetail;
