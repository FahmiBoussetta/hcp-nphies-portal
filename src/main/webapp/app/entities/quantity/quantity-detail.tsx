import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './quantity.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const QuantityDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const quantityEntity = useAppSelector(state => state.quantity.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="quantityDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.quantity.detail.title">Quantity</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{quantityEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="hcpNphiesPortalApp.quantity.value">Value</Translate>
            </span>
          </dt>
          <dd>{quantityEntity.value}</dd>
          <dt>
            <span id="unit">
              <Translate contentKey="hcpNphiesPortalApp.quantity.unit">Unit</Translate>
            </span>
          </dt>
          <dd>{quantityEntity.unit}</dd>
        </dl>
        <Button tag={Link} to="/quantity" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/quantity/${quantityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default QuantityDetail;
