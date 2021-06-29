import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './total.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TotalDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const totalEntity = useAppSelector(state => state.total.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="totalDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.total.detail.title">Total</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{totalEntity.id}</dd>
          <dt>
            <span id="category">
              <Translate contentKey="hcpNphiesPortalApp.total.category">Category</Translate>
            </span>
          </dt>
          <dd>{totalEntity.category}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="hcpNphiesPortalApp.total.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{totalEntity.amount}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.total.claimResponse">Claim Response</Translate>
          </dt>
          <dd>{totalEntity.claimResponse ? totalEntity.claimResponse.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/total" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/total/${totalEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TotalDetail;
