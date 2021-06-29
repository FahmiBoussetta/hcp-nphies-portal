import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './communication-request.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CommunicationRequestDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const communicationRequestEntity = useAppSelector(state => state.communicationRequest.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="communicationRequestDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.communicationRequest.detail.title">CommunicationRequest</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{communicationRequestEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="hcpNphiesPortalApp.communicationRequest.value">Value</Translate>
            </span>
          </dt>
          <dd>{communicationRequestEntity.value}</dd>
          <dt>
            <span id="system">
              <Translate contentKey="hcpNphiesPortalApp.communicationRequest.system">System</Translate>
            </span>
          </dt>
          <dd>{communicationRequestEntity.system}</dd>
          <dt>
            <span id="parsed">
              <Translate contentKey="hcpNphiesPortalApp.communicationRequest.parsed">Parsed</Translate>
            </span>
          </dt>
          <dd>{communicationRequestEntity.parsed}</dd>
          <dt>
            <span id="limitDate">
              <Translate contentKey="hcpNphiesPortalApp.communicationRequest.limitDate">Limit Date</Translate>
            </span>
          </dt>
          <dd>
            {communicationRequestEntity.limitDate ? (
              <TextFormat value={communicationRequestEntity.limitDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.communicationRequest.subject">Subject</Translate>
          </dt>
          <dd>{communicationRequestEntity.subject ? communicationRequestEntity.subject.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.communicationRequest.about">About</Translate>
          </dt>
          <dd>{communicationRequestEntity.about ? communicationRequestEntity.about.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.communicationRequest.sender">Sender</Translate>
          </dt>
          <dd>{communicationRequestEntity.sender ? communicationRequestEntity.sender.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.communicationRequest.communication">Communication</Translate>
          </dt>
          <dd>{communicationRequestEntity.communication ? communicationRequestEntity.communication.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/communication-request" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/communication-request/${communicationRequestEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CommunicationRequestDetail;
