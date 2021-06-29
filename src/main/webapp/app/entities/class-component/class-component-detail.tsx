import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './class-component.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ClassComponentDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const classComponentEntity = useAppSelector(state => state.classComponent.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="classComponentDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.classComponent.detail.title">ClassComponent</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{classComponentEntity.id}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="hcpNphiesPortalApp.classComponent.type">Type</Translate>
            </span>
          </dt>
          <dd>{classComponentEntity.type}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="hcpNphiesPortalApp.classComponent.value">Value</Translate>
            </span>
          </dt>
          <dd>{classComponentEntity.value}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="hcpNphiesPortalApp.classComponent.name">Name</Translate>
            </span>
          </dt>
          <dd>{classComponentEntity.name}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.classComponent.coverage">Coverage</Translate>
          </dt>
          <dd>{classComponentEntity.coverage ? classComponentEntity.coverage.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/class-component" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/class-component/${classComponentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClassComponentDetail;
