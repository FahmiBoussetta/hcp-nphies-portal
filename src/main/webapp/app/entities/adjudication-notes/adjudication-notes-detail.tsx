import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './adjudication-notes.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AdjudicationNotesDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const adjudicationNotesEntity = useAppSelector(state => state.adjudicationNotes.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="adjudicationNotesDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.adjudicationNotes.detail.title">AdjudicationNotes</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{adjudicationNotesEntity.id}</dd>
          <dt>
            <span id="note">
              <Translate contentKey="hcpNphiesPortalApp.adjudicationNotes.note">Note</Translate>
            </span>
          </dt>
          <dd>{adjudicationNotesEntity.note}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.adjudicationNotes.adjudicationItem">Adjudication Item</Translate>
          </dt>
          <dd>{adjudicationNotesEntity.adjudicationItem ? adjudicationNotesEntity.adjudicationItem.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/adjudication-notes" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/adjudication-notes/${adjudicationNotesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AdjudicationNotesDetail;
