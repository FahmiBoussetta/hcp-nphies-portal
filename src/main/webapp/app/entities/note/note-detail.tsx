import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './note.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const NoteDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const noteEntity = useAppSelector(state => state.note.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="noteDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.note.detail.title">Note</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{noteEntity.id}</dd>
          <dt>
            <span id="text">
              <Translate contentKey="hcpNphiesPortalApp.note.text">Text</Translate>
            </span>
          </dt>
          <dd>{noteEntity.text}</dd>
          <dt>
            <span id="author">
              <Translate contentKey="hcpNphiesPortalApp.note.author">Author</Translate>
            </span>
          </dt>
          <dd>{noteEntity.author}</dd>
          <dt>
            <span id="time">
              <Translate contentKey="hcpNphiesPortalApp.note.time">Time</Translate>
            </span>
          </dt>
          <dd>{noteEntity.time ? <TextFormat value={noteEntity.time} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.note.communication">Communication</Translate>
          </dt>
          <dd>{noteEntity.communication ? noteEntity.communication.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.note.communicationRequest">Communication Request</Translate>
          </dt>
          <dd>{noteEntity.communicationRequest ? noteEntity.communicationRequest.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/note" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/note/${noteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NoteDetail;
