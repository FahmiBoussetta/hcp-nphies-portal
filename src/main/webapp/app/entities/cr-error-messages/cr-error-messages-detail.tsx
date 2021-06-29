import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './cr-error-messages.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CRErrorMessagesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const cRErrorMessagesEntity = useAppSelector(state => state.cRErrorMessages.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cRErrorMessagesDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.cRErrorMessages.detail.title">CRErrorMessages</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cRErrorMessagesEntity.id}</dd>
          <dt>
            <span id="message">
              <Translate contentKey="hcpNphiesPortalApp.cRErrorMessages.message">Message</Translate>
            </span>
          </dt>
          <dd>{cRErrorMessagesEntity.message}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.cRErrorMessages.claimResponse">Claim Response</Translate>
          </dt>
          <dd>{cRErrorMessagesEntity.claimResponse ? cRErrorMessagesEntity.claimResponse.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/cr-error-messages" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cr-error-messages/${cRErrorMessagesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CRErrorMessagesDetail;
