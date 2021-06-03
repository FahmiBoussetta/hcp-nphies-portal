import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './quantity.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IQuantityDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const QuantityDetail = (props: IQuantityDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { quantityEntity } = props;
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

const mapStateToProps = ({ quantity }: IRootState) => ({
  quantityEntity: quantity.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(QuantityDetail);
