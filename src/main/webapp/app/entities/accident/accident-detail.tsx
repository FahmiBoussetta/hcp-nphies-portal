import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './accident.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AccidentDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const accidentEntity = useAppSelector(state => state.accident.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="accidentDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.accident.detail.title">Accident</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{accidentEntity.id}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="hcpNphiesPortalApp.accident.date">Date</Translate>
            </span>
          </dt>
          <dd>{accidentEntity.date ? <TextFormat value={accidentEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="hcpNphiesPortalApp.accident.type">Type</Translate>
            </span>
          </dt>
          <dd>{accidentEntity.type}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.accident.location">Location</Translate>
          </dt>
          <dd>{accidentEntity.location ? accidentEntity.location.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/accident" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/accident/${accidentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AccidentDetail;
