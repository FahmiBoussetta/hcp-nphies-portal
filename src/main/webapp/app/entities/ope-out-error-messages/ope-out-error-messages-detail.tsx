import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './ope-out-error-messages.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const OpeOutErrorMessagesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const opeOutErrorMessagesEntity = useAppSelector(state => state.opeOutErrorMessages.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="opeOutErrorMessagesDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.opeOutErrorMessages.detail.title">OpeOutErrorMessages</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{opeOutErrorMessagesEntity.id}</dd>
          <dt>
            <span id="message">
              <Translate contentKey="hcpNphiesPortalApp.opeOutErrorMessages.message">Message</Translate>
            </span>
          </dt>
          <dd>{opeOutErrorMessagesEntity.message}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.opeOutErrorMessages.operationOutcome">Operation Outcome</Translate>
          </dt>
          <dd>{opeOutErrorMessagesEntity.operationOutcome ? opeOutErrorMessagesEntity.operationOutcome.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/ope-out-error-messages" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ope-out-error-messages/${opeOutErrorMessagesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OpeOutErrorMessagesDetail;
