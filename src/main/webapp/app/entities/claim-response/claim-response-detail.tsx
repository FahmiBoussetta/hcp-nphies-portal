import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './claim-response.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ClaimResponseDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const claimResponseEntity = useAppSelector(state => state.claimResponse.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="claimResponseDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.claimResponse.detail.title">ClaimResponse</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{claimResponseEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="hcpNphiesPortalApp.claimResponse.value">Value</Translate>
            </span>
          </dt>
          <dd>{claimResponseEntity.value}</dd>
          <dt>
            <span id="system">
              <Translate contentKey="hcpNphiesPortalApp.claimResponse.system">System</Translate>
            </span>
          </dt>
          <dd>{claimResponseEntity.system}</dd>
          <dt>
            <span id="parsed">
              <Translate contentKey="hcpNphiesPortalApp.claimResponse.parsed">Parsed</Translate>
            </span>
          </dt>
          <dd>{claimResponseEntity.parsed}</dd>
          <dt>
            <span id="outcome">
              <Translate contentKey="hcpNphiesPortalApp.claimResponse.outcome">Outcome</Translate>
            </span>
          </dt>
          <dd>{claimResponseEntity.outcome}</dd>
        </dl>
        <Button tag={Link} to="/claim-response" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/claim-response/${claimResponseEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClaimResponseDetail;
