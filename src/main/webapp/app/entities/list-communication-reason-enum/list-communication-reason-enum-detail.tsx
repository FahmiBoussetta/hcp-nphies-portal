import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './list-communication-reason-enum.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ListCommunicationReasonEnumDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const listCommunicationReasonEnumEntity = useAppSelector(state => state.listCommunicationReasonEnum.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="listCommunicationReasonEnumDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.listCommunicationReasonEnum.detail.title">ListCommunicationReasonEnum</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{listCommunicationReasonEnumEntity.id}</dd>
          <dt>
            <span id="cr">
              <Translate contentKey="hcpNphiesPortalApp.listCommunicationReasonEnum.cr">Cr</Translate>
            </span>
          </dt>
          <dd>{listCommunicationReasonEnumEntity.cr}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.listCommunicationReasonEnum.communication">Communication</Translate>
          </dt>
          <dd>{listCommunicationReasonEnumEntity.communication ? listCommunicationReasonEnumEntity.communication.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/list-communication-reason-enum" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/list-communication-reason-enum/${listCommunicationReasonEnumEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ListCommunicationReasonEnumDetail;
