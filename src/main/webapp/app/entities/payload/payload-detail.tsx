import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './payload.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PayloadDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const payloadEntity = useAppSelector(state => state.payload.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="payloadDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.payload.detail.title">Payload</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{payloadEntity.id}</dd>
          <dt>
            <span id="contentString">
              <Translate contentKey="hcpNphiesPortalApp.payload.contentString">Content String</Translate>
            </span>
          </dt>
          <dd>{payloadEntity.contentString}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.payload.contentAttachment">Content Attachment</Translate>
          </dt>
          <dd>{payloadEntity.contentAttachment ? payloadEntity.contentAttachment.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.payload.contentReference">Content Reference</Translate>
          </dt>
          <dd>{payloadEntity.contentReference ? payloadEntity.contentReference.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.payload.communication">Communication</Translate>
          </dt>
          <dd>{payloadEntity.communication ? payloadEntity.communication.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.payload.communicationRequest">Communication Request</Translate>
          </dt>
          <dd>{payloadEntity.communicationRequest ? payloadEntity.communicationRequest.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/payload" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/payload/${payloadEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PayloadDetail;
