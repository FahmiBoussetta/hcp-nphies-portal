import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './cov-eli-resp-error-messages.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CovEliRespErrorMessagesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const covEliRespErrorMessagesEntity = useAppSelector(state => state.covEliRespErrorMessages.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="covEliRespErrorMessagesDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.covEliRespErrorMessages.detail.title">CovEliRespErrorMessages</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{covEliRespErrorMessagesEntity.id}</dd>
          <dt>
            <span id="message">
              <Translate contentKey="hcpNphiesPortalApp.covEliRespErrorMessages.message">Message</Translate>
            </span>
          </dt>
          <dd>{covEliRespErrorMessagesEntity.message}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.covEliRespErrorMessages.coverageEligibilityResponse">
              Coverage Eligibility Response
            </Translate>
          </dt>
          <dd>
            {covEliRespErrorMessagesEntity.coverageEligibilityResponse ? covEliRespErrorMessagesEntity.coverageEligibilityResponse.id : ''}
          </dd>
        </dl>
        <Button tag={Link} to="/cov-eli-resp-error-messages" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cov-eli-resp-error-messages/${covEliRespErrorMessagesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CovEliRespErrorMessagesDetail;
