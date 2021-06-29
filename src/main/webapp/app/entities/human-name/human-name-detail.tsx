import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './human-name.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const HumanNameDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const humanNameEntity = useAppSelector(state => state.humanName.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="humanNameDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.humanName.detail.title">HumanName</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{humanNameEntity.id}</dd>
          <dt>
            <span id="family">
              <Translate contentKey="hcpNphiesPortalApp.humanName.family">Family</Translate>
            </span>
          </dt>
          <dd>{humanNameEntity.family}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.humanName.patient">Patient</Translate>
          </dt>
          <dd>{humanNameEntity.patient ? humanNameEntity.patient.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.humanName.practitioner">Practitioner</Translate>
          </dt>
          <dd>{humanNameEntity.practitioner ? humanNameEntity.practitioner.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/human-name" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/human-name/${humanNameEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default HumanNameDetail;
