import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './cov-eli-error-messages.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CovEliErrorMessagesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const covEliErrorMessagesEntity = useAppSelector(state => state.covEliErrorMessages.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="covEliErrorMessagesDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.covEliErrorMessages.detail.title">CovEliErrorMessages</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{covEliErrorMessagesEntity.id}</dd>
          <dt>
            <span id="message">
              <Translate contentKey="hcpNphiesPortalApp.covEliErrorMessages.message">Message</Translate>
            </span>
          </dt>
          <dd>{covEliErrorMessagesEntity.message}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.covEliErrorMessages.coverageEligibilityRequest">
              Coverage Eligibility Request
            </Translate>
          </dt>
          <dd>{covEliErrorMessagesEntity.coverageEligibilityRequest ? covEliErrorMessagesEntity.coverageEligibilityRequest.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/cov-eli-error-messages" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cov-eli-error-messages/${covEliErrorMessagesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CovEliErrorMessagesDetail;
